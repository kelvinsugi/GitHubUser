package com.kelvinsugiarto.gituserapp.data.api

import com.kelvinsugiarto.gituserapp.data.model.CurrencyModel
import com.kelvinsugiarto.gituserapp.data.model.LatestCurrencyModel
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenExchangeRatesAPI {
    @GET("currencies.json")
    suspend fun getListCurrency(@Query("app_id") app_id: String,
                                @Query("show_inactive") show_inactive: Boolean):Map<String,String>

    @GET("latest.json")
    suspend fun getLatestCurrency(@Query("app_id") app_id: String,
                                  @Query("base") base : String): LatestCurrencyModel
}