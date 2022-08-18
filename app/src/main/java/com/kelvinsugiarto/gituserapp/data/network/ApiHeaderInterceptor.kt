package com.kelvinsugiarto.gituserapp.data.network

import com.kelvinsugiarto.gituserapp.BuildConfig.API_KEY_GITHUB
import okhttp3.Interceptor
import okhttp3.Response

class ApiHeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("Accept","application/json")
                .addHeader("Authorization", API_KEY_GITHUB)
                .build()
        )
    }
}