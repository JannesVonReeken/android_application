package com.example.androidjannes.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material3.Scaffold
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidjannes.data.NbaSeasonsState
import com.example.androidjannes.R
import com.example.androidjannes.data.StartScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen( //Starting screen of the app
    navController: NavController,
    startScreenViewModel: StartScreenViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            AppHeader()
        },
        content = { contentPadding ->
            Column(
                modifier =
                Modifier.padding(contentPadding)
            ) {
                when (val seasonState =
                    startScreenViewModel.seasons) { //Checks the status of the API call
                    is NbaSeasonsState.Success -> { //Data is available - Success
                        SeasonsView(
                            seasons = seasonState.seasons,
                            navController = navController,
                            startScreenViewModel = startScreenViewModel
                        )
                    }

                    NbaSeasonsState.Loading -> { //Loading
                        OnLoading()
                    }

                    NbaSeasonsState.Error -> { //No data found or not available
                        OnError()
                    }
                }
            }

        },
    )
}

@Composable
fun AppHeader( //Header for the App
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .height(56.dp)
            .background(Color.Blue)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row {
            Text(
                text = stringResource(R.string.Title),
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }
}

@Composable
fun SeasonsView(
    //Final view of the list
    seasons: List<Int>,
    navController: NavController,
    startScreenViewModel: StartScreenViewModel
) {
    Infobox()
    SeasonsList(
        seasons = seasons,
        navController = navController,
        startScreenViewModel = startScreenViewModel
    )
}

@Composable
fun SeasonsList(
    //Shows the seasons from the API call from the startviewmodel
    modifier: Modifier = Modifier,
    seasons: List<Int>,
    navController: NavController,
    startScreenViewModel: StartScreenViewModel
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    )
    {
        items(seasons) { itemSeason -> //Every item stands for a season, listed on the screen
            Box(
                modifier = Modifier
                    .testTag(stringResource(R.string.SeasonsTag))
                    .fillMaxWidth()
                    .clickable { startScreenViewModel.onItemClick(navController, itemSeason) }
            ) {
                Text(
                    text = itemSeason.toString(),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            }
            Divider(modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun Infobox() { //Shows the advice to choose a season
    Box(
        modifier = Modifier
            .background(Color(240, 241, 242))
            .fillMaxWidth()
            .height(56.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(stringResource(R.string.ChooseASeason))
    }
}

