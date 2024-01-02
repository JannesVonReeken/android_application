package com.example.androidjannes

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun SetupNavGraph(
    navController : NavHostController,
    sharedViewModel: SharedViewModel
)
{
    NavHost(
        navController = navController,
        startDestination = Screen.Start.route)
    {
        composable(route = Screen.Start.route){
            StartScreen(navController = navController, sharedViewModel = sharedViewModel)
        }

        composable(route = Screen.Info.route){
            InfoScreen(navController = navController, sharedViewModel = sharedViewModel )
        }
    }
}