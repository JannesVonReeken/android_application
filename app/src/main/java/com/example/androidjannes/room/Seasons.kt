package com.example.androidjannes.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "nbaSeasons")
data class Seasons(
    @TypeConverters(Converters::class)
    val seasons: List<Int>, //List of the Nba seasons for the offline usage
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)