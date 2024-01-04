package com.example.androidjannes

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class StartScreenViewModelFactory(private val seasonsDao: SeasonsDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StartScreenViewModel::class.java)) {
            return StartScreenViewModel(seasonsDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}