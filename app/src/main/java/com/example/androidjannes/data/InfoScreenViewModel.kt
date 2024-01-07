package com.example.androidjannes.data

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.androidjannes.network.NbaApi
import com.example.androidjannes.network.StandingData
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * Sealed interface representing the different states.
 */
sealed interface NbaStandingsState {
    data class Success(val standings: List<StandingData>) : NbaStandingsState
    object Loading : NbaStandingsState
    object Error : NbaStandingsState
}

/**
 * ViewModel for the information screen, responsible for managing NBA standings data.
 *
 * @property savedStateHandle SavedStateHandle to keep the state of the selected season.
 */
class InfoScreenViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    var standings: NbaStandingsState by mutableStateOf(NbaStandingsState.Loading)
        private set //Real NBA standing state

    var eastConferenceScrollState: LazyListState = LazyListState()
        private set //Saves the scroll state for the conference

    var westConferenceScrollState: LazyListState = LazyListState()
        private set //Saves the scroll state for the conference

    /**
     * Resets the scroll state for a choosen conference.
     *
     * @param scrollState The LazyListState to be reset.
     */
    fun resetScrollState(scrollState: LazyListState) {
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

    /**
     * Makes an API call to retrieve NBA standings based on the selected season.
     */
    private fun getStandings() {
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

    /**
     * Allowing the addition of a savedStateHandle for creating the ViewModel.
     */
    companion object { //Allows us  to add an savedStateHandle -> so we can keep the selected season
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val savedStateHandle = extras.createSavedStateHandle()

                return InfoScreenViewModel(
                    savedStateHandle
                ) as T
            }
        }
    }
}