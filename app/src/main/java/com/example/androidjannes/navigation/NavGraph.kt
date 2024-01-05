package com.example.androidjannes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.androidjannes.data.InfoScreenViewModel
import com.example.androidjannes.data.StartScreenViewModel
import com.example.androidjannes.ui.screens.InfoScreen
import com.example.androidjannes.ui.screens.StartScreen

//Implementing the navigation for the app
@Composable
fun SetupNavGraph(
    navController : NavHostController,
    startScreenViewModel: StartScreenViewModel,
    infoScreenViewModel: InfoScreenViewModel,
)
{
    NavHost(
        navController = navController,
        startDestination = Screen.Start.route) //Startscreen is startdestination
    {
        composable(route = Screen.Start.route){
            StartScreen(navController = navController, startScreenViewModel = startScreenViewModel, infoScreenViewModel = infoScreenViewModel)
        }

        composable(route = Screen.Info.route){//Infoscreen is this rout
            InfoScreen(navController = navController, infoScreenViewModel = infoScreenViewModel)
        }
    }
}