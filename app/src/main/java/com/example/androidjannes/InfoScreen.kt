package com.example.androidjannes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.androidjannes.network.StandingData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoScreen(
    navController: NavController,
) {
    val infoViewModel = viewModel<InfoScreenViewModel>()

    Scaffold(
        topBar = {
            AppHeader()
        },
        content = { contentPadding ->
            Column(
                modifier =
                Modifier.padding(contentPadding)
            ) {
                when (val standingsState = infoViewModel.standings) {
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
