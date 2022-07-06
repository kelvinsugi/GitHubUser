package com.kelvinsugiarto.gituserapp.di.module

import com.kelvinsugiarto.gituserapp.data.network.UserEntityDataImpl
import com.kelvinsugiarto.gituserapp.domain.usecase.GithubUserUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppBindings {

    @Binds
    abstract fun bindNetworkRepository(userEntityDataImpl: UserEntityDataImpl): GithubUserUseCase

}