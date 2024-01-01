package com.example.androidjannes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(
    navController: NavController
){
    val startViewModel = viewModel<StartScreenViewModel>()

    Scaffold(
        topBar = {
            AppHeader()
        },
        content = { contentPadding ->
            Column(
                modifier =
                Modifier.padding(contentPadding)
            ) {
                when(val seasonState = startViewModel.seasons){
                    is NbaSeasonsState.Success -> {
                        Seasons(seasons = seasonState.seasons, navController = navController)
                    }
                    NbaSeasonsState.Loading -> {
                        Text(text = "Loading...")
                    }
                    NbaSeasonsState.Error -> {
                        Text(text = "Error loading seasons")
                    }
                }
            }

        },
        bottomBar = {
            NavigationBar()
        }
    )
}

@Composable
fun AppHeader(
    modifier: Modifier = Modifier
){
    Box(
        modifier
            .height(56.dp)
            .background(Color.Green)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = stringResource(R.string.Title),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun Seasons(
    modifier: Modifier = Modifier,
    seasons : List<Int>,
    navController: NavController
){
    LazyColumn(modifier = modifier
        .fillMaxSize())
    {
        items(seasons) {year ->
            Text(text = year.toString(),
                modifier = Modifier.clickable { onItemClick(navController) })
        }
    }
}

fun onItemClick(
    navController: NavController
){
    navController.navigate(Screen.Info.route)
}

@Composable
fun NavigationBar(
    modifier: Modifier = Modifier
){
    NavigationBar(
        modifier = modifier
    ){
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            selected = true,
            onClick = {}
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Build,
                    contentDescription = null
                )
            },
            selected = true,
            onClick = {}
        )
    }
}
