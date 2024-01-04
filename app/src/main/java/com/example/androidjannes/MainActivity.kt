package com.example.androidjannes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.androidjannes.ui.theme.AndroidJannesTheme


class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController
    private lateinit var seasonsDao: SeasonsDao
    private val startScreenViewModel : StartScreenViewModel by viewModels(){
        StartScreenViewModelFactory(seasonsDao = seasonsDao) }
    private val infoScreenViewModel : InfoScreenViewModel by viewModels()
    //private val sharedViewModel: SharedViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val seasonsDatabase = Room.databaseBuilder(
            applicationContext,
            SeasonsDatabase::class.java, "seasons_database"
        ).build()

        seasonsDao = seasonsDatabase.dao
        setContent {
            AndroidJannesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    navController = rememberNavController()
                    SetupNavGraph(
                        navController = navController,
                        startScreenViewModel = startScreenViewModel,
                        infoScreenViewModel = infoScreenViewModel,
                        //sharedViewModel = sharedViewModel
                    )
                }
            }
        }
    }
}
