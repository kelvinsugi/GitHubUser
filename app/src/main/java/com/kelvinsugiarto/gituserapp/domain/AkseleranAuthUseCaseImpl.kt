package com.kelvinsugiarto.gituserapp.domain

import com.kelvinsugiarto.gituserapp.data.model.CredentialModel
import com.kelvinsugiarto.gituserapp.data.model.DataResult
import com.kelvinsugiarto.gituserapp.data.network.AkseleranAuthDataImpl
import javax.inject.Inject

class AkseleranAuthUseCaseImpl @Inject constructor(
    private val akseleranAuthDataImpl: AkseleranAuthDataImpl
) {
    suspend fun  login(credentialModel: CredentialModel):DataResult<Any> {
        return akseleranAuthDataImpl.login(credentialModel)
    }

    suspend fun logout(authKey: String) : DataResult<Any> {
        return akseleranAuthDataImpl.logout(authKey)
    }
}