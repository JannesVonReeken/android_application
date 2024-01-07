package com.example.androidjannes.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Representing the local database for storing NBA seasons.
 **/
@Database(
    entities = [Seasons::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class SeasonsDatabase : RoomDatabase() {
    abstract val dao: SeasonsDao
}