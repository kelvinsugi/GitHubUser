package com.kelvinsugiarto.gituserapp.di.module

import com.kelvinsugiarto.gituserapp.data.api.AkseleranLoginApi
import com.kelvinsugiarto.gituserapp.data.api.GithubUserApi
import com.kelvinsugiarto.gituserapp.data.api.OpenExchangeRatesAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

//    @Provides
//    fun providesGithubUserApi(retrofit: Retrofit): GithubUserApi = retrofit.create(GithubUserApi::class.java)

    @Provides
    fun providesOpenExchangeRatesAPI(retrofit: Retrofit): OpenExchangeRatesAPI = retrofit.create(OpenExchangeRatesAPI::class.java)

    @Provides
    fun providesAkseleranLoginAPI(retrofit: Retrofit): AkseleranLoginApi = retrofit.create(AkseleranLoginApi::class.java)

}