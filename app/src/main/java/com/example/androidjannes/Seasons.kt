package com.example.androidjannes

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "nbaSeasons")
data class Seasons(
    @TypeConverters(Converters::class)
    val seasons: List<Int>,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)