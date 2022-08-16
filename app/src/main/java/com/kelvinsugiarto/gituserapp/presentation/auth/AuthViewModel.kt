package com.kelvinsugiarto.gituserapp.presentation.auth


import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import com.kelvinsugiarto.gituserapp.data.model.CredentialModel
import com.kelvinsugiarto.gituserapp.data.model.DataResult
import com.kelvinsugiarto.gituserapp.domain.AkseleranAuthUseCaseImpl
import com.kelvinsugiarto.gituserapp.util.wrapEspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCaseImpl: AkseleranAuthUseCaseImpl):ViewModel() {

    private val loginLiveDataPrivate = MutableLiveData<DataResult<Any>>()
    val loginLiveData: LiveData<DataResult<Any>> = loginLiveDataPrivate

    fun login(email: String, password: String) {
        val credentialModel = CredentialModel()
        credentialModel.email = email
        credentialModel.password = password
        loginLiveDataPrivate.value = DataResult.Loading("Loading")
        viewModelScope.launch{
            wrapEspressoIdlingResource {
                val result = authUseCaseImpl.login(credentialModel)
                loginLiveDataPrivate.value = result
            }
        }
    }

    fun logout(authKey: String){
        loginLiveDataPrivate.value = DataResult.Loading("Loading")
        viewModelScope.launch {
            wrapEspressoIdlingResource {
                val result = authUseCaseImpl.logout(authKey)
                loginLiveDataPrivate.value = DataResult.Success(result)
            }
        }
    }


//    sealed class AuthUIState {
//        object Empty : AuthUIState()
//        object Loading : AuthUIState()
//        class Loaded(val dataResult: DataResult<Any>) : AuthUIState()
//        class Error(@StringRes val message: Int) : AuthUIState()
//    }

    fun getObserverState() = loginLiveData

}