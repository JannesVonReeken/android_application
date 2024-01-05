package com.example.androidjannes

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Seasons::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class SeasonsDatabase : RoomDatabase() { //Room database
    abstract val dao: SeasonsDao
}