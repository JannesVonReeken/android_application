package com.example.androidjannes

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidjannes.network.NbaApi
import com.example.androidjannes.network.StandingData
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface NbaStandingsState{ //Error, Loading & Success states for the loading
    data class Success(val standings : List<StandingData>) : NbaStandingsState
    object Loading : NbaStandingsState
    object Error : NbaStandingsState
}
class InfoScreenViewModel() : ViewModel() {
    var standings: NbaStandingsState by mutableStateOf(NbaStandingsState.Loading)
        private set //Real NBA standing state

    var eastConferenceScrollState: LazyListState = LazyListState()
        private set //Saves the scroll state for the conference

    var westConferenceScrollState: LazyListState = LazyListState()
        private set //Saves the scroll state for the conference

    private val _selectedSeason = mutableStateOf(0)
    val selectedSeason: State<Int> get() = _selectedSeason //gets the "_selectedSeason"

    fun setSelectedYear(season : Int){ //sets the "_selectedSeason" to the Season which was picked on the Startscreen
        _selectedSeason.value = season
        getStandings()
    }

    private fun getStandings(){ //Makes the API call to get the current standings, depending on the chooses season
        val currentSelectedSeason = selectedSeason.value
        viewModelScope.launch {
            standings = try {
                val standingResponse = NbaApi.retrofitService.getStandings(season = currentSelectedSeason)
                NbaStandingsState.Success(standingResponse.response) //State changes to success
            } catch (e: IOException){
                NbaStandingsState.Error //State changes to error
            }
        }
    }

    fun calculateDisplayValues(standingData: StandingData): String { //Gets the different values for the teamcards
        return "W: ${standingData.win.total} L: ${standingData.loss.total} Rank: ${standingData.conference.rank}"
    }
}