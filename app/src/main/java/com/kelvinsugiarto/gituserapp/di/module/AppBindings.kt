package com.kelvinsugiarto.gituserapp.di.module

import com.kelvinsugiarto.gituserapp.data.network.AkseleranAuthDataImpl
import com.kelvinsugiarto.gituserapp.data.network.OpenExchangeRatesDataImpl
import com.kelvinsugiarto.gituserapp.domain.usecase.AkseleranAuthUseCase
import com.kelvinsugiarto.gituserapp.domain.usecase.OpenExchangeRatesUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppBindings {

    @Binds
    abstract fun bindNetworkRepository(openExchangeRatesDataImpl: OpenExchangeRatesDataImpl): OpenExchangeRatesUseCase

    @Binds
    abstract fun bindAkseleranUseCase(akseleranAuthDataImpl: AkseleranAuthDataImpl):AkseleranAuthUseCase
}