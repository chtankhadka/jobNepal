package com.chetan.jobnepal.utils

import android.content.Context
import com.chetan.jobnepal.data.models.Address
import com.google.gson.Gson

object JsonReader {
    fun readAndDeserializeJson(context: Context, fileName: String): Address?{
        val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        val gson = Gson()
        return gson.fromJson(jsonString,Address::class.java)
    }
}