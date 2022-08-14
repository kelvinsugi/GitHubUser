package com.kelvinsugiarto.gituserapp.data.model

import java.io.Serializable

class UnauthorizedResponse : Serializable {
    var timestamp : String? = null
    var status : Int? = null
    var error : String? = null
    var message : String? = null
    var path : String? = null
}