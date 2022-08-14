package com.kelvinsugiarto.gituserapp.data.model

import java.sql.Timestamp

data class LatestCurrencyModel(
    val timestamp: Int,
    val base: String,
    val rates : Map<String,Double>)

