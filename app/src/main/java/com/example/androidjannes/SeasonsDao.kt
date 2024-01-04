package com.example.androidjannes

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert


@Dao
interface SeasonsDao {
    @Query("SELECT * FROM nbaSeasons LIMIT 1")
    suspend fun getSeasons(): Seasons?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeasons(seasons: Seasons)
}