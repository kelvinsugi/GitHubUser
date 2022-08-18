package com.kelvinsugiarto.gituserapp.domain

import com.kelvinsugiarto.gituserapp.data.model.UserItemsModel
import com.kelvinsugiarto.gituserapp.data.model.UserModel
import com.kelvinsugiarto.gituserapp.data.model.UsersListModel
import com.kelvinsugiarto.gituserapp.domain.usecase.GithubUserUseCase
import javax.inject.Inject

class GithubUserUseCaseImpl @Inject constructor(
    private val githubUserUseCase: GithubUserUseCase) {

    suspend fun  getListUsers():List<UsersListModel> {
        return githubUserUseCase.getListUsers()
    }

    suspend fun getUser(username:String): UserModel {
        return githubUserUseCase.getUser(username)
    }

    suspend fun searchUser(query: String):UserItemsModel{
        return githubUserUseCase.searchUser(query)
    }
}