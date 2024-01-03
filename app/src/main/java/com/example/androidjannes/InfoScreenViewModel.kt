package com.example.androidjannes

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

sealed interface NbaStandingsState{
    data class Success(val standings : List<StandingData>) : NbaStandingsState
    object Loading : NbaStandingsState
    object Error : NbaStandingsState
}
class InfoScreenViewModel() : ViewModel() {
    var standings: NbaStandingsState by mutableStateOf(NbaStandingsState.Loading)
        private set

    private val _selectedYear = mutableStateOf(0)
    val selectedYear: State<Int> get() = _selectedYear

    fun setSelectedYear(year : Int){
        _selectedYear.value = year
        getStandings()
    }

    private fun getStandings(){
        val currentSelectedYear = selectedYear.value
        viewModelScope.launch {
            standings = try {
                val standingResponse = NbaApi.retrofitService.getStandings(season = currentSelectedYear)
                NbaStandingsState.Success(standingResponse.response)
            } catch (e: IOException){
                NbaStandingsState.Error
            }
        }
    }
}