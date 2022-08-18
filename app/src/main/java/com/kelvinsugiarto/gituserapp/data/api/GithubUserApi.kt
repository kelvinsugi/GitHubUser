package com.kelvinsugiarto.gituserapp.data.api

import com.kelvinsugiarto.gituserapp.data.model.UserItemsModel
import com.kelvinsugiarto.gituserapp.data.model.UserModel
import com.kelvinsugiarto.gituserapp.data.model.UsersListModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

//this is the service interface that will be implemented by retrofit

interface GithubUserApi {
    @GET("/users")
    suspend fun getListUsers():List<UsersListModel>

    @GET("/users/{username}")
    suspend fun getUser(@Path("username") userName:String):UserModel

    @GET("/search/users")
    suspend fun searchUser(
        @Query("q") username: String?
    ): UserItemsModel
}