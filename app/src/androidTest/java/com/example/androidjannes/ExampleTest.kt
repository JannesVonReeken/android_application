package com.example.androidjannes


import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.example.androidjannes.data.InfoScreenViewModel
import com.example.androidjannes.data.StartScreenViewModel
import com.example.androidjannes.navigation.SetupNavGraph
import com.example.androidjannes.repositorys.SeasonsRepository
import com.example.androidjannes.ui.screens.InfoScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import io.mockk.mockk
import junit.framework.TestCase.assertEquals

class ExampleTest {

    @get:Rule
    val rule = createComposeRule()
    lateinit var navController: TestNavHostController

    @Before
    fun setUpNavhost(){
        rule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            SetupNavGraph(navController = navController)
        }
    }

    @Test
    fun verify_StartDestinationIsHomeScreen() {
        rule
            .onNodeWithText("Choose a season:")
            .assertIsDisplayed()
    }

    @Test
    fun performClick_OnRandomFactButton_navigatesToDetailScreen() {
       rule.onNodeWithText("2019")
            .performClick()

        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals(route, "info_screen/{selectedSeason}")
    }

//    @Test
//    fun onSeasonClickShowsSeasonInfo() {
//        val context = ApplicationProvider.getApplicationContext<Context>()
//        val seasonsRepository = mockk<SeasonsRepository>()
//        val viewModelFactory = StartScreenViewModelFactory(seasonsRepository)
//
//        val startScreenViewModel = ViewModelProvider(
//            LocalViewModelStoreOwner.current!!,
//            factory = viewModelFactory
//        ).get(StartScreenViewModel::class.java)
//
//        val infoScreenViewModel = ViewModelProvider(
//            LocalViewModelStoreOwner.current!!,
//            factory = viewModelFactory
//        ).get(InfoScreenViewModel::class.java)
//
//        rule.setContent {
//            InfoScreen(
//                navController = navController,
//                infoScreenViewModel = infoScreenViewModel
//            )
//        }
//        rule.onNodeWithTag("HomeButton", useUnmergedTree = true).performClick()
//    }
}