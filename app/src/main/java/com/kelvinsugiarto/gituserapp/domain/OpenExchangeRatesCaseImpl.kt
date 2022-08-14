package com.kelvinsugiarto.gituserapp.domain

import com.kelvinsugiarto.gituserapp.data.model.CurrencyModel
import com.kelvinsugiarto.gituserapp.data.model.LatestCurrencyModel
import com.kelvinsugiarto.gituserapp.domain.usecase.OpenExchangeRatesUseCase
import javax.inject.Inject

class OpenExchangeRatesCaseImpl @Inject constructor(
    private val openExchangeRatesUseCase: OpenExchangeRatesUseCase
) {

    suspend fun  getListCurrency():List<CurrencyModel> {
        return openExchangeRatesUseCase.getCurrencyList()
    }

    suspend fun getLatestCurrency(base:String): LatestCurrencyModel {
        return openExchangeRatesUseCase.getLatestCurrency(base)
    }
}