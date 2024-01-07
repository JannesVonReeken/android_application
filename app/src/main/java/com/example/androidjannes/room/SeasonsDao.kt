package com.example.androidjannes.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Dao interface for interacting with the seasons database.
 */
@Dao
interface SeasonsDao {
    @Query("SELECT * FROM nbaSeasons LIMIT 1")
    suspend fun getSeasons(): Seasons? //Gets the seasons from the seasons entity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeasons(seasons: Seasons) //Inserts seasons entity - helps to update the database
}