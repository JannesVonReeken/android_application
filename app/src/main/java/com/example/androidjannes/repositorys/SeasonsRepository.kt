package com.example.androidjannes.repositorys

import com.example.androidjannes.room.Seasons
import com.example.androidjannes.room.SeasonsDao

/**
 * Repository class responsible for separating the data access layer.
 *
 * @param seasonsDao Dao for interacting with the local seasons database.
 */
class SeasonsRepository(private val seasonsDao: SeasonsDao) {

    suspend fun getSeasons(): Seasons?{ //Get seasons
        return seasonsDao.getSeasons()
    }

    suspend fun insertSeasons(seasons: Seasons){ //Insert seasons
        seasonsDao.insertSeasons(seasons)
    }
}
