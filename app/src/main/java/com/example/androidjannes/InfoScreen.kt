package com.example.androidjannes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun InfoScreen(
    navController: NavController
){
    val infoViewModel = viewModel<InfoScreenViewModel>()

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
        ) {
            Text(
                modifier = Modifier.clickable {
                    navController.navigate(Screen.Start.route){
                        popUpTo(Screen.Start.route){
                            inclusive = true
                        }
                    }
                    infoViewModel.onClickChange()
                },
                text = stringResource(R.string.text2_name) )
        }
    }
}