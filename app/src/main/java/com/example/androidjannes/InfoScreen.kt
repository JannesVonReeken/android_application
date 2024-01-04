package com.example.androidjannes

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
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
                        Standings(infoScreenViewModel = infoScreenViewModel, standings = standingsState.standings )
                    }

                    NbaStandingsState.Loading -> {
                        Text(text = "Loading...")
                    }

                    NbaStandingsState.Error -> {
                        Text(text = "Error loading seasons")
                    }
                }
            }

        },
    )
}

@Composable
fun Standings(
    infoScreenViewModel: InfoScreenViewModel,
    modifier: Modifier = Modifier,
    standings : List<StandingData>,
) {
    val eastConference = standings.filter { it.conference.name.equals("East", ignoreCase = true) }
    val westConference = standings.filter { it.conference.name.equals("West", ignoreCase = true) }
    val configuration = LocalConfiguration.current
    when (configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
            Column(modifier = modifier.fillMaxSize()) {
                Text(stringResource(R.string.EastConference))
                LazyColumn(modifier = modifier
                    .weight(1f),
                    state = infoScreenViewModel.eastConferenceScrollState
                ){
                    items(eastConference.sortedWith(compareByDescending<StandingData> { it.win.total }.thenBy { it.loss.total })) { item ->
                        TeamCard(infoScreenViewModel = infoScreenViewModel, nbaTeam = item)
                    }
                }
                Spacer(modifier = Modifier.padding(36.dp))
                Text(stringResource(R.string.WestConference))
                LazyColumn(modifier = modifier
                    .weight(1f),
                    state = infoScreenViewModel.westConferenceScrollState){
                    items(westConference.sortedWith(compareByDescending<StandingData> { it.win.total }.thenBy { it.loss.total })){ item ->
                        TeamCard(infoScreenViewModel = infoScreenViewModel, nbaTeam = item)
                    }
                }
            }
        }
    }
    when(configuration.orientation){
        Configuration.ORIENTATION_LANDSCAPE -> {
            Row(modifier = modifier.fillMaxSize()) {
                // Linke Hälfte
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(16.dp)
                ) {
                    Text(stringResource(R.string.EastConference))
                    LazyColumn(
                        state = infoScreenViewModel.eastConferenceScrollState
                    ) {
                        items(eastConference.sortedWith(compareByDescending<StandingData> { it.win.total }.thenBy { it.loss.total })) { item ->
                            TeamCard(infoScreenViewModel = infoScreenViewModel, nbaTeam = item)
                        }
                    }
                }

                // Rechte Hälfte
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(16.dp)
                ) {
                    Text(stringResource(R.string.WestConference))
                    LazyColumn(
                        state = infoScreenViewModel.westConferenceScrollState
                    ) {
                        items(westConference.sortedWith(compareByDescending<StandingData> { it.win.total }.thenBy { it.loss.total })) { item ->
                            TeamCard(infoScreenViewModel = infoScreenViewModel, nbaTeam = item)
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun TeamCard(
    nbaTeam : StandingData,
    infoScreenViewModel: InfoScreenViewModel)
{
    val backgroundColor = if (nbaTeam.conference.name.equals("East", ignoreCase = true)) {
        Color(red = 1, green = 87, blue = 155)
    } else {
        Color.Red
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, top = 5.dp, bottom = 5.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
            Column(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
            ) 
            { 
                Text(text = nbaTeam.team.name)
                Text(infoScreenViewModel.calculateDisplayValues(nbaTeam)) 
                AsyncImage(
                    model = nbaTeam.team.logo,
                    contentDescription = "Lol",
                    modifier = Modifier
                        .size(75.dp, 75.dp))
            }
    }
}

