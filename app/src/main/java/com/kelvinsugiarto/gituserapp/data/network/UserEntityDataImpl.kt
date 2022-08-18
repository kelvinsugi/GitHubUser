package com.kelvinsugiarto.gituserapp.data.network

import com.kelvinsugiarto.gituserapp.data.api.GithubUserApi
import com.kelvinsugiarto.gituserapp.data.model.UserItemsModel
import com.kelvinsugiarto.gituserapp.data.model.UserModel
import com.kelvinsugiarto.gituserapp.data.model.UsersListModel
import com.kelvinsugiarto.gituserapp.domain.usecase.GithubUserUseCase
import javax.inject.Inject

class UserEntityDataImpl @Inject constructor(
    private val githubUserApi: GithubUserApi
) : GithubUserUseCase {
    override suspend fun getListUsers(): List<UsersListModel> {
        return githubUserApi.getListUsers()
    }

    override suspend fun getUser(username: String): UserModel {
        return githubUserApi.getUser(username)
    }

    override suspend fun searchUser(query: String): UserItemsModel {
        return githubUserApi.searchUser(query)
    }
}