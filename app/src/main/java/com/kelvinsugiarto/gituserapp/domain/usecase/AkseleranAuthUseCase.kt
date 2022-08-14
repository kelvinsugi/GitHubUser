package com.kelvinsugiarto.gituserapp.domain.usecase

import com.kelvinsugiarto.gituserapp.data.model.*
import okhttp3.Response

interface AkseleranAuthUseCase {
    suspend fun login(credentialModel: CredentialModel):DataResult<Any>
    suspend fun logout(authKey: String):DataResult<Any>
}