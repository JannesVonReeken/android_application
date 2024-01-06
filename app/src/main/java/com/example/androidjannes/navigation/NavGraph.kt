package com.example.androidjannes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.androidjannes.data.InfoScreenViewModel
import com.example.androidjannes.data.StartScreenViewModel
import com.example.androidjannes.ui.screens.InfoScreen
import com.example.androidjannes.ui.screens.StartScreen

//Implementing the navigation for the app
@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Start.route
    ) //Startscreen is startdestination
    {
        composable(route = Screen.Start.route) {
            StartScreen(
                navController = navController
            )
        }

        composable(
            route = "${Screen.Info.route}/{selectedSeason}",
            arguments = listOf(
                navArgument("selectedSeason") { type = NavType.IntType}
            )
        ) {backStackEntry ->
            val selectedSeason = backStackEntry.arguments?.getInt("selectedSeason") ?: -1
            InfoScreen(navController = navController, selectedSeason = selectedSeason)
        }
    }
}