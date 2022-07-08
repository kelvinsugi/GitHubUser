package com.kelvinsugiarto.gituserapp.presentation.user_detail

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelvinsugiarto.gituserapp.data.model.UserModel
import com.kelvinsugiarto.gituserapp.domain.GithubUserUseCaseImpl
import com.kelvinsugiarto.gituserapp.util.ExceptionParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val githubUserUseCaseImpl: GithubUserUseCaseImpl
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserDetailUIState>(UserDetailUIState.Empty)
    val uiState: StateFlow<UserDetailUIState> = _uiState

    sealed class UserDetailUIState {
        object Empty : UserDetailUIState()
        object Loading : UserDetailUIState()
        class Loaded(val itemState: UserModel) : UserDetailUIState()
        class Error(@StringRes val message: Int) : UserDetailUIState()
    }

    fun getUserDetail(username:String) {
        _uiState.value = UserDetailUIState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = githubUserUseCaseImpl.getUser(username)

                _uiState.value = UserDetailUIState.Loaded(result)
            } catch (error: Exception) {
                _uiState.value = UserDetailUIState.Error(ExceptionParser.getMessage(error))
            }
        }
    }

}