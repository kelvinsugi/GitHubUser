package com.kelvinsugiarto.gituserapp.data.network

import com.kelvinsugiarto.gituserapp.BuildConfig
import com.kelvinsugiarto.gituserapp.data.api.AkseleranLoginApi
import com.kelvinsugiarto.gituserapp.data.api.OpenExchangeRatesAPI
import com.kelvinsugiarto.gituserapp.data.model.CurrencyModel
import com.kelvinsugiarto.gituserapp.data.model.LatestCurrencyModel
import com.kelvinsugiarto.gituserapp.domain.usecase.OpenExchangeRatesUseCase
import javax.inject.Inject

class OpenExchangeRatesDataImpl @Inject constructor(
    private val openExchangeRatesAPI: OpenExchangeRatesAPI,
):OpenExchangeRatesUseCase  {
    override suspend fun getCurrencyList(): List<CurrencyModel> {
        val map = openExchangeRatesAPI.getListCurrency(BuildConfig.API_KEY ,false)
        var arrayList = ArrayList<CurrencyModel>()
        for ((key, value) in map) {
            println("$key = $value")
            arrayList.add(CurrencyModel(key,value))
        }

        return arrayList
    }

    override suspend fun getLatestCurrency(base: String): LatestCurrencyModel {
       return openExchangeRatesAPI.getLatestCurrency(BuildConfig.API_KEY,base)
    }
}