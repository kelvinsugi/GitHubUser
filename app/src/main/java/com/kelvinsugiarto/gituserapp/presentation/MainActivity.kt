package com.kelvinsugiarto.gituserapp.presentation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kelvinsugiarto.gituserapp.data.model.UsersListModel
import com.kelvinsugiarto.gituserapp.databinding.ActivityMainBinding
import com.kelvinsugiarto.gituserapp.presentation.user_list.UserListViewModel
import com.kelvinsugiarto.gituserapp.presentation.user_list.adapter.UserListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var userListAdapter: UserListAdapter

    lateinit var mContext : Context

    private val userListViewModel: UserListViewModel by viewModels()

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

    private fun fetchUserList() {
        userListViewModel.getUserLists()
    }

    fun initUI(){
        binding.apply {
            rvUsersList.run {
                userListAdapter = UserListAdapter {

                }
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = userListAdapter
            }

            setSupportActionBar(toolbarMain) //Set toolbar
//            supportActionBar?.setDisplayShowTitleEnabled(false);
            supportActionBar?.title = "Github User List"
        }

    }

    fun initListener(){
        binding.srlMain.setOnRefreshListener {
            fetchUserList()
        }

    }

    fun initObserver(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userListViewModel.uiState.collect {
                        state ->
                    when(state) {
                        is UserListViewModel.UserListUIState.Loaded -> {
                            onLoaded(state.itemState)
                        }
                        is UserListViewModel.UserListUIState.Error -> showError(state.message)
                        else -> showLoading(true)
                    }
                }
            }
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
}