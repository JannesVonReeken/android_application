package com.example.androidjannes

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters { //Converter for List<int> to string or from string to List<int> because Room can't handle a list of integers (ChatGPT & Stackoverflow helped alot, especially here)
    @TypeConverter
    fun fromList(value: List<Int>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toList(value: String): List<Int>? {
        val listType = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(value, listType)
    }
}