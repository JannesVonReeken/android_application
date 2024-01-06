package com.example.androidjannes.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.androidjannes.data.InfoScreenViewModel
import com.example.androidjannes.data.NbaStandingsState
import com.example.androidjannes.R
import com.example.androidjannes.navigation.Screen
import com.example.androidjannes.network.StandingData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoScreen(
    navController: NavController,
    infoScreenViewModel: InfoScreenViewModel = viewModel(),
    selectedSeason: Int
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
                when (val standingsState =
                    infoScreenViewModel.standings) { //Standingsstate from the infoscreenViewmodel
                    is NbaStandingsState.Success -> { //On Success it loads the standings composable
                        Standings(
                            infoScreenViewModel = infoScreenViewModel,
                            standings =
                            standingsState.standings
                        )
                    }

                    NbaStandingsState.Loading -> { //On loading
                        OnLoading()
                    }

                    NbaStandingsState.Error -> { //If something didn't worked
                        OnError()
                    }
                }
            }

        },
        bottomBar = {
            NavigationBar(
                navController = navController,
                infoScreenViewModel = infoScreenViewModel
            )
        }
    )
}

@Composable
fun Standings(
    infoScreenViewModel: InfoScreenViewModel,
    modifier: Modifier = Modifier,
    standings: List<StandingData>,
) {
    val eastConference = standings.filter {
        it.conference.name.equals(
            "East",
            ignoreCase = true
        )
    } //Splits the teams in eastern & western conference, depending on the standing
    val westConference = standings.filter { it.conference.name.equals("West", ignoreCase = true) }
    val configuration = LocalConfiguration.current
    when (configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> { //Portrait view
            if (eastConference.isNotEmpty()) {
                PortraitStandings(
                    infoScreenViewModel = infoScreenViewModel,
                    eastConference = eastConference,
                    westConference = westConference
                )
            } else {
                NoDataAvailable()
            }
        }
    }
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> { //Landscape view
            if (eastConference.isNotEmpty()) {
                StandingsLandscape(
                    modifier = modifier,
                    eastConference = eastConference,
                    westConference = westConference,
                    infoScreenViewModel = infoScreenViewModel
                )
            } else {
                NoDataAvailable()
            }
        }
    }
}

@Composable
fun PortraitStandings( //Final view for the portrait mode
    modifier: Modifier = Modifier,
    infoScreenViewModel: InfoScreenViewModel,
    eastConference: List<StandingData>,
    westConference: List<StandingData>
) {
    Column(modifier = modifier.fillMaxSize()) {
        ConferenceHeader(conference = stringResource(R.string.EastConference))
        TeamList(
            modifier = modifier.weight(1f),
            infoScreenViewModel = infoScreenViewModel,
            conferenceList = eastConference,
            scrollState = infoScreenViewModel.eastConferenceScrollState
        )
        ConferenceHeader(conference = stringResource(R.string.WestConference))
        TeamList(
            modifier = modifier.weight(1f),
            infoScreenViewModel = infoScreenViewModel,
            conferenceList = westConference,
            scrollState = infoScreenViewModel.westConferenceScrollState
        )
    }
}

@Composable
fun StandingsLandscape(
    //Final view for the landscape mode
    modifier: Modifier,
    eastConference: List<StandingData>,
    westConference: List<StandingData>,
    infoScreenViewModel: InfoScreenViewModel,
) {
    Row {
        StandingsColumnLandscape(
            modifier = modifier.weight(1f), //Set weight to split the row in the middle
            conference = eastConference,
            infoScreenViewModel = infoScreenViewModel,
            conferenceName = stringResource(R.string.EastConference),
            scrollState = infoScreenViewModel.eastConferenceScrollState //Hold the scrollstate
        )
        StandingsColumnLandscape(
            modifier = modifier.weight(1f), //Set weight to split the row in the middle
            conference = westConference,
            infoScreenViewModel = infoScreenViewModel,
            conferenceName = stringResource(R.string.WestConference),
            scrollState = infoScreenViewModel.westConferenceScrollState //Hold the scrollstate
        )
    }
}

@Composable
fun StandingsColumnLandscape( //Combination of the conference Name & the conference list
    modifier: Modifier,
    conference: List<StandingData>,
    infoScreenViewModel: InfoScreenViewModel,
    conferenceName: String,
    scrollState: LazyListState
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    )
    {
        ConferenceHeader(conferenceName)
        TeamList(
            modifier = modifier,
            infoScreenViewModel = infoScreenViewModel,
            conferenceList = conference,
            scrollState = scrollState
        )
    }
}

@Composable
fun ConferenceHeader( //The header for each conference - text is centered
    conference: String
) {
    Spacer(modifier = Modifier.padding(8.dp))
    Box(
        modifier = Modifier
            .background(Color(240, 241, 242))
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    )
    {
        Text(conference)
    }
    Spacer(modifier = Modifier.padding(8.dp))
}

@Composable
fun TeamList( //Creates & orders the list for the choosen conference
    modifier: Modifier,
    infoScreenViewModel: InfoScreenViewModel,
    conferenceList: List<StandingData>,
    scrollState: LazyListState
) {
    LazyColumn(
        modifier = modifier,
        state = scrollState
    ) {
        items(conferenceList.sortedWith(compareBy<StandingData> { it.conference.rank }.thenBy { it.conference.win }
            .thenBy { it.conference.loss })) { item -> //Order by Rank, Wins & Losses
            TeamCard(
                infoScreenViewModel = infoScreenViewModel,
                nbaTeam = item
            )
        }
    }
}

@Composable
fun TeamCard( //Creates the Teamcards with the names,ranks,scores and logos
    nbaTeam: StandingData,
    infoScreenViewModel: InfoScreenViewModel
) {
    val backgroundColor = if (nbaTeam.conference.name.equals(
            "East",
            ignoreCase = true
        )
    ) { //Changes color of the card, depending on the conference (red is normaly the western & blue the eastern conference)
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
        Column(
            modifier = Modifier
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
                model = ImageRequest.Builder(LocalContext.current)
                    .data(nbaTeam.team.logo)
                    .crossfade(true)
                    .build(),
                contentDescription = "${nbaTeam.team.name} logo",
                modifier = Modifier
                    .size(75.dp, 75.dp)
            )
        }
    }
}

@Composable
fun OnLoading() { //If its loading
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(stringResource(R.string.Loading))
    }
}

@Composable
fun OnError() { //If something went wrong
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(stringResource(R.string.SeasonNotLoading))
    }
}

@Composable
fun NoDataAvailable() { //If no datas are available for this season
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(stringResource(R.string.SeasonNotAvailable))
    }
}

@Composable
fun NavigationBar( //Navigationbar to navigate back to the seasons list
    modifier: Modifier = Modifier,
    navController: NavController,
    infoScreenViewModel: InfoScreenViewModel
) {
    androidx.compose.material3.NavigationBar(
        modifier = modifier
    ) {
        NavigationBarItem(
            icon = {
                Column {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "HomeButton",
                            modifier = Modifier
                                .testTag("home")
                        )
                        Text(text = "Home", modifier = Modifier.alpha(0f))
                    }
                }
            },
            selected = true,
            onClick = {
                infoScreenViewModel.resetScrollState(infoScreenViewModel.westConferenceScrollState) //Resets the scrollstates after going back to the seasons screen
                infoScreenViewModel.resetScrollState(infoScreenViewModel.eastConferenceScrollState)
                navController.navigate(Screen.Start.route)
            }
        )
    }
}

