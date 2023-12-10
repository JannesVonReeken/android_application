package com.example.androidjannes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class InfoScreenViewModel : ViewModel() {

    var text by mutableStateOf("Zurück")

    fun onClickChange(){
        text = "prank"
    }
}