package com.kelvinsugiarto.gituserapp

import com.kelvinsugiarto.gituserapp.data.model.CurrencyModel
import com.kelvinsugiarto.gituserapp.utils.JsonToPojoConverter
import java.util.*

object MockReturnValueTest {
    val ALL_CURRENCIES_LIST = "currencieslist.json"

     fun getCurrencyListDummy(): List<CurrencyModel>{
        val response = JsonToPojoConverter.convertJson<Map<String,String>>(ALL_CURRENCIES_LIST)
         var arrayList = ArrayList<CurrencyModel>()
         for ((key, value) in response) {
             println("$key = $value")
             arrayList.add(CurrencyModel(key,value))
         }
        return arrayList
    }

    fun getResponseFromFile(fileName: String):String{
        return JsonToPojoConverter.readFromFile(fileName)
    }
}