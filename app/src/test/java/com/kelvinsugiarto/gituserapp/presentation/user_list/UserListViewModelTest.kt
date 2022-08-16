package com.kelvinsugiarto.gituserapp.presentation.user_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.kelvinsugiarto.gituserapp.data.model.CurrencyModel
import com.kelvinsugiarto.gituserapp.domain.OpenExchangeRatesCaseImpl
import com.kelvinsugiarto.gituserapp.domain.usecase.OpenExchangeRatesUseCase
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever

import junit.framework.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
internal class UserListViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    var useCase =  mock(OpenExchangeRatesUseCase::class.java)

    @Mock
    lateinit var observerState : Observer<UserListViewModel.CurrencyListUIState>

    @InjectMocks
    var impl = OpenExchangeRatesCaseImpl(useCase)

//    @Mock
//    private lateinit var currencyModelList: List<CurrencyModel>


    val viewmodel by lazy { UserListViewModel(impl) }


    @Before
    fun setUp() {
        reset(useCase, observerState)
        MockitoAnnotations.initMocks(this)
//        useCase = mock(OpenExchangeRatesUseCase::class.java)
//        impl = spy(OpenExchangeRatesCaseImpl(useCase))
        observerState = mock()
//        viewmodel.uiCurrencyState.observeForever(observerState)



        // do something if required
    }

    @After
    fun tearDown() {
    }

    @Test
    fun currencyListTest_expected_result() {
        val _uiCurrencyState = MutableLiveData<UserListViewModel.CurrencyListUIState>(
            UserListViewModel.CurrencyListUIState.Empty)

        val response = arrayListOf<CurrencyModel>()
        runBlocking {
            whenever(useCase.getCurrencyList())
                .thenReturn(response)
        }

        val listString = ArrayList<String>()
        for(i in response){
            listString.add(i.currencyName)
        }
        _uiCurrencyState.postValue(UserListViewModel.CurrencyListUIState.Loaded(listString,response))


        runBlocking { assertNotNull(useCase.getCurrencyList()) }
        runBlocking { assertNotNull(viewmodel.uiCurrencyState) }
        runBlocking { assertEquals(_uiCurrencyState.value, viewmodel.uiCurrencyState.value) }
//        runBlocking { assertEquals(response, useCase.getCurrencyList()) }
    }

}

//
//    @Test
//    fun calculateResult(baseRate: Double, amount: Double) {
//        assertEquals(0, calculateResult(2.0,0.0))
//    }
//
//    @Test
//    fun calculateResultDecimal(baseRate: Double, amount: Double) {
//        assertEquals(20000.0, calculateResult(2000.0,10.0))
//    }
//}