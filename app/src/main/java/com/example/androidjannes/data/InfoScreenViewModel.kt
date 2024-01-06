package com.example.androidjannes.data

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.androidjannes.network.NbaApi
import com.example.androidjannes.network.StandingData
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface NbaStandingsState { //Error, Loading & Success states for the loading
    data class Success(val standings: List<StandingData>) : NbaStandingsState
    object Loading : NbaStandingsState
    object Error : NbaStandingsState
}

class InfoScreenViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    var standings: NbaStandingsState by mutableStateOf(NbaStandingsState.Loading)
        private set //Real NBA standing state

    var eastConferenceScrollState: LazyListState = LazyListState()
        private set //Saves the scroll state for the conference

    var westConferenceScrollState: LazyListState = LazyListState()
        private set //Saves the scroll state for the conference

    fun resetScrollState(scrollState: LazyListState) { //Resets the scrollstate from the western or eastern conference
        viewModelScope.launch {
            scrollState.scrollToItem(0)
        }
    }

    private val _selectedSeason = mutableStateOf(0)
    val selectedSeason: State<Int> get() = _selectedSeason //gets the "_selectedSeason"

    init {
        setSelectedYear(
            savedStateHandle.get<Int>("selectedSeason") ?: -1
        )
    }

    fun setSelectedYear(season: Int) { //sets the "_selectedSeason" to the Season which was picked on the Startscreen
        _selectedSeason.value = season
        getStandings()
    }

    private fun getStandings() { //Makes the API call to get the current standings, depending on the chooses season
        val currentSelectedSeason = selectedSeason.value
        viewModelScope.launch {
            standings = try {
                val standingResponse =
                    NbaApi.retrofitService.getStandings(season = currentSelectedSeason)
                NbaStandingsState.Success(standingResponse.response) //State changes to success
            } catch (e: IOException) {
                NbaStandingsState.Error //State changes to error
            }
        }
    }

    fun calculateDisplayValues(standingData: StandingData): String { //Gets the different values for the teamcards
        return "W: ${standingData.win.total} L: ${standingData.loss.total} Rank: ${standingData.conference.rank}"
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])
                // Create a SavedStateHandle for this ViewModel from extras
                val savedStateHandle = extras.createSavedStateHandle()

                return InfoScreenViewModel(
                    savedStateHandle
                ) as T
            }
        }
    }
}