package com.kelvinsugiarto.gituserapp.data.model

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("login")
    val login:String,

    @SerializedName("id")
    val id:Int,

    @SerializedName("node_id")
    val node_id:String,

    @SerializedName("avatar_url")
    val avatar_url:String?,

    @SerializedName("name")
    val name:String?,

    @SerializedName("location")
    val location:String?,

    @SerializedName("email")
    val email:String?,

    @SerializedName("followers")
    val followers:Int,

    @SerializedName("following")
    val following:Int,
)
