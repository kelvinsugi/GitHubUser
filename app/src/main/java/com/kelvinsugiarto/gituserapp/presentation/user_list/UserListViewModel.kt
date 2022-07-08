package com.kelvinsugiarto.gituserapp.presentation.user_list

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelvinsugiarto.gituserapp.data.model.UsersListModel
import com.kelvinsugiarto.gituserapp.domain.GithubUserUseCaseImpl
import com.kelvinsugiarto.gituserapp.util.ExceptionParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val githubUserUseCaseImpl: GithubUserUseCaseImpl
    ) : ViewModel() {

    private val _uiState = MutableStateFlow<UserListUIState>(UserListUIState.Empty)
    val uiState: StateFlow<UserListUIState> = _uiState


    fun getUserLists() {
        _uiState.value = UserListUIState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = githubUserUseCaseImpl.getListUsers()

                _uiState.value = UserListUIState.Loaded(result)
            } catch (error: Exception) {
                _uiState.value = UserListUIState.Error(ExceptionParser.getMessage(error))
            }
        }
    }


    sealed class UserListUIState {
        object Empty : UserListUIState()
        object Loading : UserListUIState()
        class Loaded(val itemState: List<UsersListModel>) : UserListUIState()
        class Error(@StringRes val message: Int) : UserListUIState()
    }
}