package com.example.androidjannes.data

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.room.Room
import com.example.androidjannes.navigation.Screen
import com.example.androidjannes.room.Seasons
import com.example.androidjannes.repositorys.SeasonsRepository
import com.example.androidjannes.network.NbaApi
import com.example.androidjannes.room.SeasonsDatabase
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * Sealed interface representing the different states.
 */
sealed interface NbaSeasonsState {
    data class Success(val seasons: List<Int>) : NbaSeasonsState
    object Loading : NbaSeasonsState
    object Error : NbaSeasonsState
}

/**
 * ViewModel for the start screen, responsible for managing NBA seasons data.
 *
 * @param application The Android application context.
 */
class StartScreenViewModel(application: Application) : AndroidViewModel(application) {

    var seasons: NbaSeasonsState by mutableStateOf(NbaSeasonsState.Loading) //List of seasons
        private set

    var seasonsRepository: SeasonsRepository //Repository for the Room Database

    init {

        val seasonsDatabase = Room.databaseBuilder( //Creating the seasonsDatabase
            getApplication<Application>().applicationContext,
            SeasonsDatabase::class.java, "seasons_database"
        ).build()

        val seasonsDao = seasonsDatabase.dao
        seasonsRepository = SeasonsRepository(seasonsDao)


        viewModelScope.launch {
            val dbSeasons =
                seasonsRepository.getSeasons()?.seasons //Gets the seasons from the database
            if (dbSeasons != null) {
                seasons =
                    NbaSeasonsState.Success(dbSeasons) //Show always first the database list to provide offline functionality
            }
        }
        getSeasons() //Trys to make the API call to update the seasons

    }

    /**
     * Makes an API call to retrieve NBA seasons and updates the local database.
     */
    private fun getSeasons() {
        viewModelScope.launch {
            seasons = try { //If the API is available "seasons" & the room database gets updated
                val seasonsResponse = NbaApi.retrofitService.getSeasons()
                val remoteSeasons = seasonsResponse.response
                seasonsRepository.insertSeasons(Seasons(remoteSeasons))
                NbaSeasonsState.Success(seasonsResponse.response)
            } catch (e: IOException) { //If the API isn't available the seasons list remains untouched
                seasons
            } catch (e: Exception){
                NbaSeasonsState.Error
            }
        }
    }

    /**
     * Handles the click event when a season is selected.
     * Navigates to the InfoScreen and adds the selected season as a parameter.
     *
     * @param navController The NavController for navigating to the infoscreen with the correct parameter
     * @param season The selected NBA season.
     */
    fun onItemClick(
        navController: NavController,
        season: Int
    ) {
        navController.navigate("${Screen.Info.route}/$season") //Adds the selected season
    }
}