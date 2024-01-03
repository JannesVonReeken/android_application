package com.example.androidjannes

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun SetupNavGraph(
    navController : NavHostController,
    startScreenViewModel: StartScreenViewModel,
    infoScreenViewModel: InfoScreenViewModel,
   // sharedViewModel: SharedViewModel
)
{
    NavHost(
        navController = navController,
        startDestination = Screen.Start.route)
    {
        composable(route = Screen.Start.route){
            StartScreen(navController = navController, startScreenViewModel = startScreenViewModel, infoScreenViewModel = infoScreenViewModel)
        }

        composable(route = Screen.Info.route){
            InfoScreen(navController = navController, infoScreenViewModel = infoScreenViewModel)
        }
    }
}