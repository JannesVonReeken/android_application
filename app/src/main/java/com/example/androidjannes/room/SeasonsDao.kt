package com.example.androidjannes.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface SeasonsDao {
    @Query("SELECT * FROM nbaSeasons LIMIT 1")
    suspend fun getSeasons(): Seasons? //Gets the from the seasons entity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeasons(seasons: Seasons) //Insets seasons entity - helps to update the database
}