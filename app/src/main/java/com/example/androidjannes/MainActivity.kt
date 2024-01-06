package com.example.androidjannes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.androidjannes.data.InfoScreenViewModel
import com.example.androidjannes.data.StartScreenViewModel
import com.example.androidjannes.navigation.SetupNavGraph
import com.example.androidjannes.repositorys.SeasonsRepository
import com.example.androidjannes.room.SeasonsDao
import com.example.androidjannes.room.SeasonsDatabase
import com.example.androidjannes.ui.theme.AndroidJannesTheme


class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidJannesTheme { //Using of this Theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    navController = rememberNavController()
                    SetupNavGraph(
                        //Setting up the navigation controller
                        navController = navController
                    )
                }
            }
        }
    }
}
