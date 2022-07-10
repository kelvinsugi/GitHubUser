package com.kelvinsugiarto.gituserapp.presentation.user_detail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kelvinsugiarto.gituserapp.R
import com.kelvinsugiarto.gituserapp.data.model.UserModel
import com.kelvinsugiarto.gituserapp.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    lateinit var mContext : Context

    private val userDetailViewModel: UserDetailViewModel by viewModels()

    var userName: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userName = intent.getStringExtra("username") ?: ""

        initUI()

        initObserver()

        fetchUserDetail(userName)

    }

    private fun initUI(){
        binding.apply {
            setSupportActionBar(detailToolbarMain) //Set toolbar

            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            supportActionBar?.title = "Detail"
        }
    }

    private fun initObserver(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userDetailViewModel.uiState.collect {
                        state ->
                    when(state) {
                        is UserDetailViewModel.UserDetailUIState.Loaded -> {
                            onLoaded(state.itemState)
                        }
                        is UserDetailViewModel.UserDetailUIState.Error -> showError(state.message)
                        else -> showLoading(true)
                    }
                }
            }
        }
    }

    private fun fetchUserDetail(username: String){
        userDetailViewModel.getUserDetail(username)
    }

    private fun onLoaded(itemDetail: UserModel){
        binding.apply {
            cvLayoutDetail.visibility = View.VISIBLE

            tvUsername.text = itemDetail.name
            if(itemDetail.email != null){
                tvUserEmail.visibility = View.VISIBLE
                tvUserEmail.text = itemDetail.email ?: ""
            } else {
                tvUserEmail.visibility = View.GONE
            }
            tvUserLocation.text = itemDetail.location ?: ""

            val options = RequestOptions()

            Glide.with(mContext).load(itemDetail.avatar_url)
                .placeholder(R.drawable.ic_avatar_1577909)
                .apply(options.circleCrop())
                .into(ivUserProfileImage)
        }

        showLoading(false)

    }

    private fun showError(@StringRes stringRes: Int) {
        Toast.makeText(mContext, stringRes, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if(isLoading)
            progressBarLoading.visibility = View.VISIBLE
            else progressBarLoading.visibility = View.GONE

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}