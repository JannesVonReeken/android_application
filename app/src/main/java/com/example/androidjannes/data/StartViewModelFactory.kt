package com.example.androidjannes.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidjannes.repositorys.SeasonsRepository

class StartScreenViewModelFactory(private val seasonsRepository: SeasonsRepository) : ViewModelProvider.Factory { //ViewmodelFactory to allow the StartScreenViewmodel to accept the seasonsDao parameter (important to safe the seasons in room)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StartScreenViewModel::class.java)) {
            return StartScreenViewModel(seasonsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}