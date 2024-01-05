package com.example.androidjannes.repositorys

import com.example.androidjannes.room.Seasons
import com.example.androidjannes.room.SeasonsDao

class SeasonsRepository(private val seasonsDao: SeasonsDao) { //Repository to separate the different layers from eachother

    suspend fun getSeasons(): Seasons?{
        return seasonsDao.getSeasons()
    }

    suspend fun insertSeasons(seasons: Seasons){
        seasonsDao.insertSeasons(seasons)
    }
}
