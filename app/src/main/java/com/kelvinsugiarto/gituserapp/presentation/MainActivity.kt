package com.kelvinsugiarto.gituserapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kelvinsugiarto.gituserapp.R
import com.kelvinsugiarto.gituserapp.data.Constants
import com.kelvinsugiarto.gituserapp.data.SharedPreferenceHelper
import com.kelvinsugiarto.gituserapp.data.SharedPreferenceHelper.get
import com.kelvinsugiarto.gituserapp.data.SharedPreferenceHelper.set
import com.kelvinsugiarto.gituserapp.data.model.*
import com.kelvinsugiarto.gituserapp.databinding.ActivityMainBinding
import com.kelvinsugiarto.gituserapp.presentation.auth.AuthActivity
import com.kelvinsugiarto.gituserapp.presentation.auth.AuthViewModel
import com.kelvinsugiarto.gituserapp.presentation.user_detail.DetailActivity
import com.kelvinsugiarto.gituserapp.presentation.user_list.UserListViewModel
import com.kelvinsugiarto.gituserapp.presentation.user_list.adapter.CurrencyResultListAdapter
import com.kelvinsugiarto.gituserapp.presentation.user_list.adapter.DropDownListAdapter
import com.kelvinsugiarto.gituserapp.presentation.user_list.adapter.UserListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.math.log


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var userListAdapter: UserListAdapter

    lateinit var mContext : Context

    private val userListViewModel: UserListViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    var arrayListCurrencyModel = ArrayList<CurrencyModel>()

    lateinit var arrayAdapter : DropDownListAdapter
    var arrayList = mutableListOf<String>()
    lateinit var currencyListAdapter: CurrencyResultListAdapter

    val cityList = mutableListOf(
        "C-Programming", "Data Structure", "Database", "Python"
    )

    var courseList = arrayOf(
        "C-Programming", "Data Structure", "Database", "Python",
        "Java", "Operating System", "Compiler Design", "Android Development"
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        initObserver()
        initListener()

        fetchUserList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sign_out -> {
                val dataLogin = SharedPreferenceHelper.customPrefs(this,Constants.SHARED_PREF_LOGIN_DATA)
                val token = dataLogin[Constants.SHARED_PREF_KEY_LOGIN_TOKEN_STRING, ""]
                authViewModel.logout(token)
            }
        }
        return super.onOptionsItemSelected(item)
    }




    private fun fetchUserList() {
        userListViewModel.getUserLists()
    }

    private fun fetchCurrencyList(){
//        userListViewModel.getCurrencyList()
//        userListViewModel.getLatestCurrency("USD")
    }


    fun initUI(){
        binding.apply {
            rvUsersList.run {
                userListAdapter = UserListAdapter {
                    val changePage = Intent(mContext, DetailActivity::class.java)
                    if (it != null) {
                        changePage.putExtra("username",it.login)
                    }
                    startActivity(changePage)

                }
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = userListAdapter
            }

            rvCurrencyList.run {
                currencyListAdapter = CurrencyResultListAdapter {
                }
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = currencyListAdapter
                addItemDecoration(DividerItemDecoration(
                    rvCurrencyList.context,
                    DividerItemDecoration.VERTICAL
                ))
            }




            arrayAdapter = DropDownListAdapter(this@MainActivity, R.layout.currency_item, arrayList)
            actvCurrency.adapter = arrayAdapter
//
//            actvCurrency.showDropDown()
//            arrayAdapter.notifyDataSetChanged()



            setSupportActionBar(toolbarMain) //Set toolbar
//            supportActionBar?.setDisplayShowTitleEnabled(false);
            supportActionBar?.title = "Currency Converter"
        }

    }

    private fun initListener(){
        binding.apply {
            srlMain.setOnRefreshListener {
                fetchCurrencyList()
            }
            actvCurrency.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                    userListViewModel.getLatestCurrency(arrayListCurrencyModel.get(p2).currencyCode)
                    Toast.makeText(mContext, arrayListCurrencyModel.get(p2).currencyCode, Toast.LENGTH_LONG).show()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

            }

            etAmount.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    val imm: InputMethodManager = getSystemService(
                        INPUT_METHOD_SERVICE
                    ) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)

                }
            }

            etAmount.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
//                    userListViewModel.calculate(etAmount.text.toString().toDouble())
                    return@setOnEditorActionListener true
                }
                false
            }


            etAmount.doAfterTextChanged {
                if (it.isNullOrBlank()) {
                    modifyText("0")
                    return@doAfterTextChanged
                }
                val originalText = it.toString()
                try {
                    if(originalText.contains(".",ignoreCase = true)){
                        val numberText = originalText
                        if (originalText != numberText) {
                            modifyText(numberText)
                        }
                    } else {
                        val numberText = originalText.toInt().toString()
                        if (originalText != numberText) {
                            modifyText(numberText)
                        }
                    }

                } catch (e: Exception) {
                    modifyText("0")
                }
            }


        }

    }

    private fun modifyText(numberText: String) {
        binding.etAmount.setText(numberText)
        binding.etAmount.setSelection(numberText.length)
    }

    fun initObserver(){

        authViewModel.loginLiveData.observe(this){
            state -> handleLoginResult(state)
        }


        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                userListViewModel.uiState.collect {
//                        state ->
//                    when(state) {
//                        is UserListViewModel.UserListUIState.Loaded -> {
//                            onLoaded(state.itemState)
//                        }
//                        is UserListViewModel.UserListUIState.Error -> showError(state.message)
//                        else -> showLoading(true)
//                    }
//                }

//                userListViewModel.uiCurrencyState.observe(this@MainActivity){
//                        state ->
//                    when(state) {
//                        is UserListViewModel.CurrencyListUIState.Loaded -> {
//                            onCurrencyLoaded(state.itemState, state.itemListCurrency)
//                        }
//                        is UserListViewModel.CurrencyListUIState.Error -> {
//                            showLoading(false)
//                            showError(state.message)}
//                        else -> showLoading(true)
//                    }
//                }



            }

            lifecycleScope.launch {
//                userListViewModel.uiCurrencyListState.collect { state ->
//                    when (state) {
//                        is UserListViewModel.CurrencyResultListUIState.Loaded -> {
//                            onCurrencyResultListLoaded(state.listCurrencyRateModel)
//                        }
//                        is UserListViewModel.CurrencyResultListUIState.Error -> {
//                            showLoading(false)
//                            showError(state.message)
//                        }
//                        else -> showLoading(true)
//                    }
//                }
            }

    }

    private fun onLoaded(userListViewModel: List<UsersListModel>) {
        binding.apply {
            userListViewModel.run {
                userListAdapter.submitList(userListViewModel)
            }
            showLoading(false)
            srlMain.isRefreshing = false

        }

    }

    private fun onCurrencyLoaded(currencyList: List<String>, currencyListModel:List<CurrencyModel>) {
        binding.apply {
            arrayListCurrencyModel.addAll(currencyListModel)
            arrayAdapter.updateData(currencyList.toMutableList())

            val selectedUSDIndex = currencyListModel.indexOfFirst{
                it.currencyCode == "USD"
            }
            actvCurrency.setSelection(selectedUSDIndex)
            showLoading(false)
            srlMain.isRefreshing = false
        }

    }

    private fun onCurrencyResultListLoaded(listCurrencyRateModel: List<CurrencyRateModel>) {
        binding.apply {
            showLoading(false)
            srlMain.isRefreshing = false
            listCurrencyRateModel.run {
                currencyListAdapter.submitList(listCurrencyRateModel)
            }
        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply{
            if(isLoading) progressBarLoading.visibility = View.VISIBLE
            else progressBarLoading.visibility = View.GONE

        }
        Timber.d("showLoading")
    }

    private fun showError(@StringRes stringRes: Int) {
        Toast.makeText(mContext, stringRes, Toast.LENGTH_SHORT).show()
    }

    private fun logOut(){
        val intent = Intent(mContext, AuthActivity::class.java)
        val pref =  SharedPreferenceHelper.customPrefs(mContext,Constants.SHARED_PREF_LOGIN_DATA)
        pref.set(Constants.SHARED_PREF_KEY_LOGIN_TOKEN_STRING, "")
        pref.set(Constants.SHARED_PREF_KEY_LOGIN_EXPIRED_STRING, "0")
        this.finish()
        startActivity(intent)
    }

    private fun handleLoginResult(status: DataResult<Any>) {
        when (status) {
            is DataResult.Loading -> showLoading(true)
            is DataResult.Success -> logOut()
            is DataResult.Unauthorized -> {
                showLoading(false)
                Snackbar.make(binding.root,"Not authorized",
                    Snackbar.LENGTH_SHORT).setAction("Dismiss"){

                }.show()
            }
            is DataResult.Error -> {
                showLoading(false)
                status.let {  Snackbar.make(binding.root,it.toString(),
                    Snackbar.LENGTH_SHORT).setAction("Dismiss"){
                }.show() }
            }
            else -> {showLoading(false)}
        }
    }
}