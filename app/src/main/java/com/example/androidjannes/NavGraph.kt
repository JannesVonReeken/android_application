package com.example.androidjannes

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun SetupNavGraph(navController : NavHostController)
{
    NavHost(
        navController = navController,
        startDestination = Screen.Start.route)
    {
        composable(route = Screen.Start.route){
            StartScreen(navController)
        }

        composable(route = Screen.Info.route){
            InfoScreen(navController)
        }
    }
}