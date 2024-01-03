package com.example.androidjannes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.androidjannes.network.StandingData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoScreen(
    navController: NavController,
    infoScreenViewModel: InfoScreenViewModel
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
                when (val standingsState = infoScreenViewModel.standings) {
                    is NbaStandingsState.Success -> {
                        Standings(standings = standingsState.standings )
                        //Test(infoScreenViewModel = infoScreenViewModel)
                    }

                    NbaStandingsState.Loading -> {
                        Text(text = "Loading...")
                    }

                    NbaStandingsState.Error -> {
                        Text(text = "Error loading seasons")
                    }

                    else -> {}
                }
            }

        },
        bottomBar = {
            NavigationBar()
        }
    )
}

@Composable
fun Standings(
    modifier: Modifier = Modifier,
    standings : List<StandingData>,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    )
    {
        items(standings) { itemYear ->
            Text(
                text = itemYear.team.name,
            )
        }
    }
}

@Composable
fun Test(
    infoScreenViewModel: InfoScreenViewModel
){
    Text(text = infoScreenViewModel.selectedYear.value.toString())
}
