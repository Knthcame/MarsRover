package com.knthcame.marsrover.ui.setup

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextReplacement
import androidx.navigation3.runtime.NavKey
import app.cash.turbine.test
import com.knthcame.marsrover.HiltTestActivity
import com.knthcame.marsrover.data.control.models.CardinalDirection
import com.knthcame.marsrover.ui.navigation.Movements
import com.knthcame.marsrover.ui.theme.MarsRoverTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class SetupScreenTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<HiltTestActivity>()

    @Before
    fun before() {
        hiltRule.inject()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun continueButton_sendsInputData_toNextScreen() = runTest {
        val navKey = MutableStateFlow<NavKey?>(null)

        composeRule.setContent {
            MarsRoverTheme {
                SetupScreenRoute(
                    onNavigate = { navKey.value = it },
                )
            }
        }

        // Input setup data
        composeRule.onNodeWithTag("setupPlateauWidthTextField")
            .performTextReplacement("5")
        composeRule.onNodeWithTag("setupPlateauHeightTextField")
            .performTextReplacement("2")
        composeRule.onNodeWithTag("setupInitialXTextField")
            .performTextReplacement("1")
        composeRule.onNodeWithTag("setupInitialYTextField")
            .performTextReplacement("2")
        composeRule.onNodeWithTag("setupInitialDirectionTextField")
            .performClick()

        val northButtonTestTag = "modalSheet${CardinalDirection.East}DirectionButton"
        composeRule.waitUntilExactlyOneExists(hasTestTag(northButtonTestTag))
        composeRule.onNodeWithTag(northButtonTestTag, useUnmergedTree = true)
            .performClick()
        composeRule.waitUntilDoesNotExist(hasTestTag(northButtonTestTag))

        navKey.test {
            skipItems(1)
            // Click continue button
            composeRule.onNodeWithTag("setupContinueButton")
                .performScrollTo()
                .performClick()

            // Assert input is communicated to next screen.
            val item = awaitItem()
            assertIs<Movements>(item)
            assertEquals(
                expected = Movements(
                    plateauHeight = 2,
                    plateauWidth = 5,
                    initialPositionX = 1,
                    initialPositionY = 2,
                    initialDirection = CardinalDirection.East,
                ),
                actual = item,
            )
        }
    }
}
