package com.kelvinsugiarto.gituserapp.domain.usecase

import com.kelvinsugiarto.gituserapp.data.model.UserModel
import com.kelvinsugiarto.gituserapp.data.model.UsersListModel

interface GithubUserUseCase {
    suspend fun getListUsers():List<UsersListModel>

    suspend fun getUser(username:String):UserModel
}