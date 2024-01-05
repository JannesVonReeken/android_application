package com.example.androidjannes

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

class SeasonsRepository(private val seasonsDao: SeasonsDao) { //Repository to separate the different layers from eachother

    suspend fun getSeasons(): Seasons?{
        return seasonsDao.getSeasons()
    }

    suspend fun insertSeasons(seasons: Seasons){
        seasonsDao.insertSeasons(seasons)
    }
}
