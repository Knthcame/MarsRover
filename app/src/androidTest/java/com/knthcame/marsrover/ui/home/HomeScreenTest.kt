package com.knthcame.marsrover.ui.home

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.knthcame.marsrover.ui.theme.MarsRoverTheme
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class HomeScreenTest {
    @get:Rule
    val composeRule = createComposeRule()

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun startButton_triggersNavigation() {
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

        assert(setupStarted)
    }
}