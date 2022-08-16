package com.kelvinsugiarto.gituserapp.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class SuccessResponse : Serializable {
    @SerializedName("access_token")
    var access_token: String? = null
    @SerializedName("expired_date")
    var expired_date: String? = null
}
