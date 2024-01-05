package com.example.androidjannes

sealed class Screen(val route : String){
    object Start : Screen("start_screen") //Defining start screen name
    object Info : Screen("info_screen") //Defining info screen name
}
