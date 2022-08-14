package com.kelvinsugiarto.gituserapp.presentation.auth

import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.kelvinsugiarto.gituserapp.data.model.CredentialModel
import com.kelvinsugiarto.gituserapp.data.model.DataResult
import com.kelvinsugiarto.gituserapp.domain.AkseleranAuthUseCaseImpl
import com.kelvinsugiarto.gituserapp.util.ExceptionParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val authUseCaseImpl: AkseleranAuthUseCaseImpl):ViewModel() {
    val _uiAuthState = MutableLiveData<AuthUIState>(AuthUIState.Empty)
    var uiAuthState: LiveData<AuthUIState> = _uiAuthState

    fun login() {
        _uiAuthState.value = AuthUIState.Loading

        viewModelScope.launch(Dispatchers.Default) {
            try {
                val credentialModel = CredentialModel()
                credentialModel.email = "test@gmail.com"
                credentialModel.password = "abcd1234567"
                val result = authUseCaseImpl.login(credentialModel)
                viewModelScope.launch(Dispatchers.Main) {
                    _uiAuthState.value = AuthUIState.Loaded(result)
                    _uiAuthState.postValue(AuthUIState.Loaded(result))
                }

                } catch (error: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    _uiAuthState.value = AuthUIState.Error(ExceptionParser.getMessage(error))
                    _uiAuthState.postValue(AuthUIState.Error(ExceptionParser.getMessage(error)))

                }
            }
        }
    }

    fun logout(){

    }


    sealed class AuthUIState {
        object Empty : AuthUIState()
        object Loading : AuthUIState()
        class Loaded(val dataResult: DataResult<Any>) : AuthUIState()
        class Error(@StringRes val message: Int) : AuthUIState()
    }
}