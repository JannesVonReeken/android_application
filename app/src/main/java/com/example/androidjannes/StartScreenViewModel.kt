package com.example.androidjannes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidjannes.network.NbaApi
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface NbaSeasonsState{
    data class Success(val seasons : List<Int>) : NbaSeasonsState
    object Loading : NbaSeasonsState
    object Error : NbaSeasonsState
}
class StartScreenViewModel : ViewModel() {

    var seasons : NbaSeasonsState by mutableStateOf(NbaSeasonsState.Loading)
        private set

    init {
        getSeasons()
    }
    private fun getSeasons(){
        viewModelScope.launch {
            seasons = try {
                val seasonsResponse = NbaApi.retrofitService.getSeasons()
                NbaSeasonsState.Success(seasonsResponse.response)
            } catch (e: IOException){
                NbaSeasonsState.Error
            }
        }
    }
}