package com.example.androidjannes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun StartScreen(
    navController: NavController
){
    val startViewModel = viewModel<StartScreenViewModel>()

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
        ) {
            Button(onClick = { startViewModel.changeOnClick(); navController.navigate(Screen.Info.route) }) {
                Text(text = startViewModel.text)
            }
        }
    }
}