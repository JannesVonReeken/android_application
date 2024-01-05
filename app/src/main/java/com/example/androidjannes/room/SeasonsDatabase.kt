package com.example.androidjannes.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.androidjannes.room.Converters
import com.example.androidjannes.room.Seasons
import com.example.androidjannes.room.SeasonsDao

@Database(
    entities = [Seasons::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class SeasonsDatabase : RoomDatabase() { //Room database
    abstract val dao: SeasonsDao
}