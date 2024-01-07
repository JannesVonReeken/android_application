package com.example.androidjannes.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Type converter class for converting between a list of integers and a string & back.
 *
 *
 */
class Converters {
    @TypeConverter //List to String
    fun fromList(value: List<Int>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter //String to List
    fun toList(value: String): List<Int>? {
        val listType = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(value, listType)
    }
}