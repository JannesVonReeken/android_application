package com.example.androidjannes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

class StartScreenViewModel : ViewModel() {

    var text by mutableStateOf("Lol")
        private set

    fun changeOnClick(){
        text = "Affe"
    }
}