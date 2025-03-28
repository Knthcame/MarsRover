package com.knthcame.marsrover.ui.home

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.knthcame.marsrover.ui.theme.MarsRoverTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.time.Duration.Companion.seconds

class HomeScreenTest {
    @get:Rule
    val composeRule = createComposeRule()

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun startButton_triggersNavigation() = runTest {
        var setupStarted = false

        composeRule.setContent {
            MarsRoverTheme {
                HomeScreenRoute(onStartSetup = {
                    setupStarted = true
                })
            }
        }

        composeRule.onNodeWithTag("homeStartButton")
            .performClick()

        delay(2.seconds)
        assert(setupStarted)
    }
}