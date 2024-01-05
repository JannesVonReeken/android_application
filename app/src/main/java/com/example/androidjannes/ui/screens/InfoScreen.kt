package com.example.androidjannes.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.androidjannes.data.InfoScreenViewModel
import com.example.androidjannes.data.NbaStandingsState
import com.example.androidjannes.R
import com.example.androidjannes.navigation.Screen
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
                when (val standingsState = infoScreenViewModel.standings) { //Standingsstate from the infoscreenViewmodel
                    is NbaStandingsState.Success -> { //On Success it loads the standings composable
                        Standings(infoScreenViewModel = infoScreenViewModel, standings = standingsState.standings )
                    }
                    NbaStandingsState.Loading -> { //On loading
                    Box(modifier = Modifier
                        .fillMaxSize(),
                        contentAlignment = Alignment.Center){
                        Text(text = "Loading...")
                    }
                    }

                    NbaStandingsState.Error -> { //If something didn't worked
                        Box(modifier = Modifier
                            .fillMaxSize(),
                            contentAlignment = Alignment.Center){
                            Text(text = "No season data available...")
                        }
                    }
                }
            }

        },
        bottomBar = { NavigationBar(navController = navController) }
    )
}

@Composable
fun Standings(
    infoScreenViewModel: InfoScreenViewModel,
    modifier: Modifier = Modifier,
    standings : List<StandingData>,
) {
    val eastConference = standings.filter { it.conference.name.equals("East", ignoreCase = true) } //Splits the teams in eastern & western conference, depending on the standing
    val westConference = standings.filter { it.conference.name.equals("West", ignoreCase = true) }
    val configuration = LocalConfiguration.current
    when (configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> { //Portrait view
                Column(modifier = modifier.fillMaxSize()) {
                    Spacer(modifier = Modifier.padding(8.dp))
                    Box(
                        modifier = Modifier
                            .background(Color(240,241,242))
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center)
                    {
                        Text(stringResource(R.string.EastConference))
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    LazyColumn(
                        modifier = modifier
                            .weight(1f),
                        state = infoScreenViewModel.eastConferenceScrollState
                    ) {
                        items(eastConference.sortedWith(compareByDescending<StandingData> { it.win.total }.thenBy { it.loss.total })) { item ->
                            TeamCard(
                                infoScreenViewModel = infoScreenViewModel,
                                nbaTeam = item
                            ) //Adds eastern conference teams & sorts them by total wins and losses
                        }
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(240,241,242)),
                        contentAlignment = Alignment.Center)
                    {
                        Text(stringResource(R.string.WestConference))
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    LazyColumn(
                        modifier = modifier
                            .weight(1f),
                        state = infoScreenViewModel.westConferenceScrollState
                    ) {
                        items(westConference.sortedWith(compareByDescending<StandingData> { it.win.total }.thenBy { it.loss.total })) { item ->
                            TeamCard(
                                infoScreenViewModel = infoScreenViewModel,
                                nbaTeam = item
                            ) //Adds western conference teams & sorts them by total wins and losses
                        }
                    }
            }
        }
    }
    when(configuration.orientation){
        Configuration.ORIENTATION_LANDSCAPE -> { //Landscape view
            Row(modifier = modifier.fillMaxSize()) {
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
                            TeamCard(infoScreenViewModel = infoScreenViewModel, nbaTeam = item) //Adds eastern conference teams & sorts them by total wins and losses
                        }
                    }
                }
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
                            TeamCard(infoScreenViewModel = infoScreenViewModel, nbaTeam = item) //Adds eastern conference teams & sorts them by total wins and losses
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
    infoScreenViewModel: InfoScreenViewModel
)
{
    val backgroundColor = if (nbaTeam.conference.name.equals("East", ignoreCase = true)) { //Changes color of the card, depending on the conference (red is normaly the western & blue the eastern conference)
        Color(red = 1, green = 87, blue = 155)
    } else {
        Color.Red
    }

    Card( //Design of the Card
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
                Text(
                    text = nbaTeam.team.name,
                    color = Color.White
                )
                Text(infoScreenViewModel.calculateDisplayValues(nbaTeam), color = Color.White)
                AsyncImage( //Load the team logos
                    model = nbaTeam.team.logo,
                    contentDescription = "${nbaTeam.team.name} logo",
                    modifier = Modifier
                        .size(75.dp, 75.dp))
            }
    }
}

@Composable
fun NavigationBar( //Navigationbar to navigate back to the seasons list
    modifier: Modifier = Modifier,
    navController: NavController
){
    androidx.compose.material3.NavigationBar(
        modifier = modifier
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null
                )
            },
            selected = true,
            onClick = {
                navController.navigate(Screen.Start.route)
            }
        )
    }
}

