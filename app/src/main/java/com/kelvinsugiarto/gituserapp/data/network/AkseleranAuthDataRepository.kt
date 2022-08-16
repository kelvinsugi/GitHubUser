package com.kelvinsugiarto.gituserapp.data.network

import com.google.gson.Gson
import com.kelvinsugiarto.gituserapp.data.api.AkseleranLoginApi
import com.kelvinsugiarto.gituserapp.data.model.CredentialModel
import com.kelvinsugiarto.gituserapp.data.model.DataResult
import com.kelvinsugiarto.gituserapp.data.model.SuccessResponse
import com.kelvinsugiarto.gituserapp.data.model.UnauthorizedResponse
import com.kelvinsugiarto.gituserapp.domain.usecase.AkseleranAuthUseCase
import javax.inject.Inject

class AkseleranAuthDataRepository @Inject constructor(
    private val akseleranLoginApi: AkseleranLoginApi
):AkseleranAuthUseCase  {
    override suspend fun login(credentialModel: CredentialModel): DataResult<Any> {
        try{
            val response = akseleranLoginApi.login(credentialModel.email,credentialModel.password)
            return when {
                response.code() == 200 -> {
                    val successResponse = response.body()
                    DataResult.Success(successResponse!!)
                }
                response.code() == 401 -> {
                    val unauthorizedResponse = Gson().fromJson(response.errorBody()!!.charStream(),UnauthorizedResponse::class.java)
                    DataResult.Unauthorized(unauthorizedResponse)
                }
                else -> {
                    DataResult.Error("Fail to map data")
                }
            }
        }catch (e: Exception){
            return DataResult.Error("Fail to get data, check your internet connection, stacktrace:$e")
        }
    }

    override suspend fun logout(authKey: String): DataResult<Any>  {
        try{
            val response = akseleranLoginApi.logout(authKey)
            return when {
                response.code() == 200 -> {
                    DataResult.Success(SuccessResponse())
                }
                response.code() == 401 -> {
                    val unauthorizedResponse = Gson().fromJson(response.body().toString(),UnauthorizedResponse::class.java)
                    DataResult.Unauthorized(unauthorizedResponse)
                }
                else -> {
                    DataResult.Error("Fail to map data")
                }
            }
        }catch (e: Exception){
            return DataResult.Error("Fail to get data, check your internet connection, stacktrace:$e")
        }
    }
}