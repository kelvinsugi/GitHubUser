package com.kelvinsugiarto.gituserapp.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.kelvinsugiarto.gituserapp.data.model.CredentialModel
import com.kelvinsugiarto.gituserapp.data.model.DataResult
import com.kelvinsugiarto.gituserapp.data.network.AkseleranAuthDataRepository
import com.kelvinsugiarto.gituserapp.data.network.BaseRepository
import javax.inject.Inject

class AkseleranAuthUseCaseImpl @Inject constructor(
    private val akseleranAuthRepository: AkseleranAuthDataRepository
):BaseRepository() {
    suspend fun  login(credentialModel: CredentialModel): DataResult<Any> {
        return akseleranAuthRepository.login(credentialModel)
    }

    suspend fun logout(authKey: String) : DataResult<Any> {
        return akseleranAuthRepository.logout(authKey)
    }
}