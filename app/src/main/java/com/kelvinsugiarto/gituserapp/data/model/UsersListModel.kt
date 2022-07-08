package com.kelvinsugiarto.gituserapp.data.model

import com.google.gson.annotations.SerializedName

data class UsersListModel(
    @SerializedName("login")
    val login:String,

    @SerializedName("id")
    val id:Int,

    @SerializedName("node_id")
    val node_id:String,

    @SerializedName("avatar_url")
    val avatar_url:String?,

    @SerializedName("url")
    val url:String?

)
