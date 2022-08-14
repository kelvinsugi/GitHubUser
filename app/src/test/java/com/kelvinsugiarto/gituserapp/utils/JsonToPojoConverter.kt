package com.kelvinsugiarto.gituserapp.utils

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.System.`in`


class JsonToPojoConverter {

    companion object{
        inline fun <T> convertJson(fileName: String): T {
            val inputStream = readFromFile(fileName)
            return Gson().fromJson(
                inputStream, object : TypeToken<T>() {}.type
            )
        }

        @Throws(IOException::class)
        fun readFromFile(name: String): String {
            val reader = InputStreamReader(this.javaClass.classLoader!!.getResourceAsStream(name))
            return reader.readText()
        }
    }


}