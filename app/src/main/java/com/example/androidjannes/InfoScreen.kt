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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
    val eastConference = standings.filter { it.conference.name.equals("East", ignoreCase = true) }
    val westConference = standings.filter { it.conference.name.equals("West", ignoreCase = true) }

    Column(modifier = modifier.fillMaxSize()) {
        if(eastConference.isNotEmpty()){
            Text(stringResource(R.string.EastConference))
            LazyColumn(modifier = modifier
                .weight(1f)
            ){
                items(eastConference.sortedWith(compareByDescending<StandingData> { it.win.total }.thenBy { it.loss.total })) { item ->
                    Text(
                        text = item.team.name,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
        if (westConference.isNotEmpty()){
            Text(stringResource(R.string.WestConference))
            LazyColumn(modifier = modifier
                .weight(1f)){
                items(westConference.sortedWith(compareByDescending<StandingData> { it.win.total }.thenBy { it.loss.total })){ item ->
                    Text(
                        text = item.team.name,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
        else{
            Text(stringResource(R.string.NoTeamsFound))
        }
    }
}
