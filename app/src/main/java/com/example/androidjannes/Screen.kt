package com.example.androidjannes

sealed class Screen(val route : String){
    object Start : Screen("start_screen")
    object Info : Screen("info_screen")
}
