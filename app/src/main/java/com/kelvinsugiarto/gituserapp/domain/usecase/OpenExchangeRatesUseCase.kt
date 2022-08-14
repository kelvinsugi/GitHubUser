package com.kelvinsugiarto.gituserapp.domain.usecase

import com.kelvinsugiarto.gituserapp.data.model.CurrencyModel
import com.kelvinsugiarto.gituserapp.data.model.LatestCurrencyModel

interface OpenExchangeRatesUseCase {
    suspend fun getCurrencyList():List<CurrencyModel>
    suspend fun getLatestCurrency(base: String):LatestCurrencyModel
}