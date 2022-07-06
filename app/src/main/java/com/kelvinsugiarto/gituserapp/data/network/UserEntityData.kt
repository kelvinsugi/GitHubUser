package com.kelvinsugiarto.gituserapp.data.network

import com.kelvinsugiarto.gituserapp.data.model.UserModel
import com.kelvinsugiarto.gituserapp.data.model.UsersListModel

interface UserEntityData {

    suspend fun getUserListData():List<UsersListModel>

    suspend fun getUserData(username: String):UserModel

}