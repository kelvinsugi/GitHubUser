package com.kelvinsugiarto.gituserapp.data.api

import com.kelvinsugiarto.gituserapp.data.model.SuccessResponse
import retrofit2.Response
import retrofit2.http.*

//this is the service interface that will be implemented by retrofit

interface AkseleranLoginApi {
    @FormUrlEncoded
    @Headers("Accept:application/json","API-KEY: tkhg564aD45sd4d46wdw99565SdFsdwdwdwQQoP11454You")
    @POST("/api/oauth/token")
    suspend fun login(@Field("email") email:String, @Field("password") password: String):Response<SuccessResponse>

    @Headers("Accept:application/json","API-KEY: tkhg564aD45sd4d46wdw99565SdFsdwdwdwQQoP11454You")
    @POST("/api/oauth/logout")
    suspend fun logout(@Header("Authorization") authKey:String):Response<Unit>
}