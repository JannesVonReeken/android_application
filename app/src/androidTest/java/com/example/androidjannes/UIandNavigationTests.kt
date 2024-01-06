package com.example.androidjannes

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.androidjannes.navigation.SetupNavGraph
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import junit.framework.TestCase.assertEquals

class UIandNavigationTests {

    @get:Rule
    val rule = createComposeRule()
    lateinit var navController: TestNavHostController

    @Before
    fun setUpNavhost() {
        rule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            SetupNavGraph(navController = navController)
        }
    }

    @Test
    fun firstScreenIsStartScreen() { //Tests if the first screen is "StartScreen" ("Choose a season" is unique on this screen)
        rule
            .onNodeWithText("Choose a season:")
            .assertIsDisplayed()
    }

    @Test
    fun onSeasonClickChangeScreenToInfoScreen() { //Tests if the screen changes from StartScreen to InfoScreen, when the user clicks on a season
        Thread.sleep(1000)
        rule.onNodeWithText("2019")
            .performClick()

        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals(route, "info_screen/{selectedSeason}")
    }

    @Test
    fun clickOnHomeButtonLeadsToStartScreen() { //Tests if the screen changes back from InfoScreen to StartScreen, when the user clicks the home button
        rule.waitUntil(timeoutMillis = 10_000) {
            rule.onAllNodesWithTag("seasons")
                .fetchSemanticsNodes().isNotEmpty()
        }
        rule.onAllNodesWithTag("seasons")[6].performClick()

        rule.waitUntil(timeoutMillis = 10_000) {
            rule.onAllNodesWithText("Home")
                .fetchSemanticsNodes().isNotEmpty()
        }

        rule.onNodeWithText("Home").performClick()

        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals(route, "start_screen")
    }
}