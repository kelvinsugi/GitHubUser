package com.kelvinsugiarto.gituserapp.presentation.user_list

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelvinsugiarto.gituserapp.data.model.CurrencyModel
import com.kelvinsugiarto.gituserapp.data.model.CurrencyRateModel
import com.kelvinsugiarto.gituserapp.data.model.UsersListModel
import com.kelvinsugiarto.gituserapp.domain.OpenExchangeRatesCaseImpl
import com.kelvinsugiarto.gituserapp.util.ExceptionParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
//    private val githubUserUseCaseImpl: GithubUserUseCaseImpl,
    private val openExchangeRatesCaseImpl: OpenExchangeRatesCaseImpl
    ) : ViewModel() {

    private val _uiState = MutableStateFlow<UserListUIState>(UserListUIState.Empty)
    val uiState: StateFlow<UserListUIState> = _uiState

    val _uiCurrencyState = MutableLiveData<CurrencyListUIState>(CurrencyListUIState.Empty)
    var uiCurrencyState: LiveData<CurrencyListUIState> = _uiCurrencyState

    private val _uiCurrencyListState = MutableStateFlow<CurrencyResultListUIState>(CurrencyResultListUIState.Empty)
    val uiCurrencyListState: StateFlow<CurrencyResultListUIState> = _uiCurrencyListState

    var arrayListCurrencyRateModel = ArrayList<CurrencyRateModel>()
    var arrayListCurrencyModel = ArrayList<CurrencyModel>()
    var baseCurrencyArrayListModel = ArrayList<CurrencyRateModel>()


    fun getUserLists() {
        _uiState.value = UserListUIState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
//                val result = githubUserUseCaseImpl.getListUsers()
//1 usd = 10000 rupiah
//                _uiState.value = UserListUIState.Loaded(result)
            } catch (error: Exception) {
                _uiState.value = UserListUIState.Error(ExceptionParser.getMessage(error))
            }
        }
    }

    fun getCurrencyList(){
        _uiCurrencyState.value = CurrencyListUIState.Loading

        viewModelScope.launch(Dispatchers.Default) {
            try {
                var result = openExchangeRatesCaseImpl.getListCurrency()
                arrayListCurrencyModel.addAll(result)

                val listString = ArrayList<String>()
                    for(i in result){
                    listString.add(i.currencyName)
                }


                viewModelScope.launch(Dispatchers.Main){
                    _uiCurrencyState.postValue(CurrencyListUIState.Loaded(listString,result))
                    _uiCurrencyState.value = CurrencyListUIState.Loaded(listString,result)
                }
              } catch (error: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    _uiCurrencyState.postValue(CurrencyListUIState.Error(ExceptionParser.getMessage(error)))
                    _uiCurrencyState.value =
                        CurrencyListUIState.Error(ExceptionParser.getMessage(error))
                }
              }
        }
    }

    fun getLatestCurrency(base:String){
        _uiCurrencyListState.value = CurrencyResultListUIState.Loading
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val result = openExchangeRatesCaseImpl.getLatestCurrency("USD")
//                val listString = ArrayList<String>()
//                for(i in result.rates){
//                    listString.add(i.currencyName)
//                }

                for(i in result.rates.entries){
                   val currencyName = arrayListCurrencyModel.filter { it.currencyCode == i.key }
                    arrayListCurrencyRateModel.add(CurrencyRateModel(currencyName[0].currencyName,i.key,i.value,0.00))
                }

//
                viewModelScope.launch(Dispatchers.Main){
                    _uiCurrencyListState.value = CurrencyResultListUIState.Loaded(arrayListCurrencyRateModel)
                }
            } catch (error: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    _uiCurrencyListState.value =
                        CurrencyResultListUIState.Error(ExceptionParser.getMessage(error))
                }
            }
        }
    }

    fun calculate(strNumber:Double){
        _uiCurrencyListState.value = CurrencyResultListUIState.Loading
        viewModelScope.launch(Dispatchers.Default) {
            for (i in arrayListCurrencyRateModel) {
                i.currencyResult = i.currencyRate * strNumber
            }
            viewModelScope.launch(Dispatchers.Main){
                _uiCurrencyListState.value = CurrencyResultListUIState.Loaded(arrayListCurrencyRateModel)
            }
        }
    }

    fun calculateResult(baseRate:Double, amount:Double):Double{
        return baseRate * amount
    }


    sealed class UserListUIState {
        object Empty : UserListUIState()
        object Loading : UserListUIState()
        class Loaded(val itemState: List<UsersListModel>) : UserListUIState()
        class Error(@StringRes val message: Int) : UserListUIState()
    }

    sealed class CurrencyListUIState {
        object Empty : CurrencyListUIState()
        object Loading : CurrencyListUIState()
        class Loaded(val itemState: List<String>, val itemListCurrency: List<CurrencyModel>) : CurrencyListUIState()
        class Error(@StringRes val message: Int) : CurrencyListUIState()
    }

    sealed class CurrencyResultListUIState {
        object Empty : CurrencyResultListUIState()
        object Loading : CurrencyResultListUIState()
        class Loaded(val listCurrencyRateModel: ArrayList<CurrencyRateModel>) : CurrencyResultListUIState()
        class Error(@StringRes val message: Int) : CurrencyResultListUIState()
    }
}