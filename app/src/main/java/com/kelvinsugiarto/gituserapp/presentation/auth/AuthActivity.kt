package com.kelvinsugiarto.gituserapp.presentation.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.kelvinsugiarto.gituserapp.data.Constants
import com.kelvinsugiarto.gituserapp.data.SharedPreferenceHelper
import com.kelvinsugiarto.gituserapp.data.SharedPreferenceHelper.set
import com.kelvinsugiarto.gituserapp.data.model.DataResult
import com.kelvinsugiarto.gituserapp.data.model.SuccessResponse
import com.kelvinsugiarto.gituserapp.databinding.ActivityAuthBinding
import com.kelvinsugiarto.gituserapp.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    lateinit var binding : ActivityAuthBinding
    private val authViewModel: AuthViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        initListener()
        initObserver()
    }


    private fun initUI(){
        supportActionBar?.hide()
        binding.toolbarTitle.text = "Test"
    }

    private fun initListener(){
        binding.apply {
            btLogin.setOnClickListener {
                if(etUsername.text?.isBlank() == true){
                    username.error = "Email empty"
                    username.errorIconDrawable = null
                }

                if(etPassword.text?.isBlank() == true){
                    password.error = "Password empty"
                    password.errorIconDrawable = null

                }

                if(etUsername.text?.isNotBlank() == true && etPassword.text?.isNotBlank() == true){
                    login(etUsername.text.toString(), etPassword.text.toString())
                    // Only runs if there is a view that is currently focused
                    this.etPassword.let { view ->
                        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                        imm?.hideSoftInputFromWindow(view.windowToken, 0)
                    }
                }
            }
        }
    }

    private fun initObserver(){
        lifecycleScope.launch {
            authViewModel.loginLiveData.observe(this@AuthActivity){
                    state -> handleLoginResult(state)
            }
        }
    }

    private fun login(email: String, password: String){
        authViewModel.login(email, password)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply{
            if(isLoading) {
                loading.visibility = View.VISIBLE
                btLogin.isEnabled = false
            }
            else {
                loading.visibility = View.GONE
                btLogin.isEnabled = true
            }

        }
        Timber.d("showLoading")
    }

    private fun saveLoginData(successResponse: SuccessResponse){
        val sharedPref = SharedPreferenceHelper.customPrefs(this,Constants.SHARED_PREF_LOGIN_DATA)
        sharedPref.set(Constants.SHARED_PREF_KEY_LOGIN_TOKEN_STRING, successResponse.access_token)
        sharedPref.set(Constants.SHARED_PREF_KEY_LOGIN_EXPIRED_STRING, successResponse.expired_date)
        finish()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun handleLoginResult(status: DataResult<Any>) {
        when (status) {
            is DataResult.Loading -> showLoading(true)
            is DataResult.Success -> status.data.let {
                val successResponse = it as SuccessResponse
                showLoading(false)
                saveLoginData(successResponse)
            }
            is DataResult.Unauthorized -> {
                showLoading(false)
                Snackbar.make(binding.root,"Wrong username or password",
                    Snackbar.LENGTH_SHORT).setAction("Dismiss"){

                }.show()
            }
            is DataResult.Error -> {
                showLoading(false)
                status.let {  Snackbar.make(binding.root,it.toString(),
                    Snackbar.LENGTH_SHORT).setAction("Dismiss"){
                }.show() }
            }
        }
    }

}