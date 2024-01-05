package com.example.androidjannes
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidjannes.network.NbaApi
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface NbaSeasonsState{ //The different states
    data class Success(val seasons : List<Int>) : NbaSeasonsState
    object Loading : NbaSeasonsState
    object Error : NbaSeasonsState
}
class StartScreenViewModel(private val seasonDao: SeasonsDao): ViewModel() {

    var seasons : NbaSeasonsState by mutableStateOf(NbaSeasonsState.Loading) //List of seasons
        private set

    init {
        viewModelScope.launch{
            val dbSeasons = seasonDao.getSeasons()?.seasons //Gets the seasons from the database
            if(dbSeasons != null){
                seasons = NbaSeasonsState.Success(dbSeasons) //Show always first the database list to provide offline functionality
            }
        }
        getSeasons() //Trys to make the API call to update the seasons

    }
    private fun getSeasons(){
        viewModelScope.launch {
            seasons = try { //If the API is available "seasons" & the room database gets updated
                    val seasonsResponse = NbaApi.retrofitService.getSeasons()
                    val remoteSeasons = seasonsResponse.response
                    seasonDao.insertSeasons(Seasons(remoteSeasons))
                    NbaSeasonsState.Success(seasonsResponse.response)
            } catch (e: IOException){ //If the API isn't available the seasons list remains untouched
                seasons
            }
        }
    }
}