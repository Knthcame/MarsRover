package com.knthcame.marsrover

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.knthcame.marsrover.data.control.models.CardinalDirection
import com.knthcame.marsrover.ui.MarsRoverNavHost
import com.knthcame.marsrover.ui.movements.Movement
import com.knthcame.marsrover.ui.theme.MarsRoverTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.compose.KoinApplication

@RunWith(AndroidJUnit4::class)
class MarsRoverTest {
    @get:Rule
    val composeRule = createComposeRule()

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun endToEndTest() {
        composeRule.setContent {
            KoinApplication(application = {
                modules(androidModule)
            }) {
                MarsRoverTheme {
                    MarsRoverNavHost(rememberNavController())
                }
            }
        }

        val timeoutMillis = 5_000L
        // Navigate to setup screen
        val startButtonTestTag = "homeStartButton"
        composeRule.waitUntilExactlyOneExists(
            hasTestTag(startButtonTestTag),
            timeoutMillis = timeoutMillis,
        )
        composeRule.onNodeWithTag(startButtonTestTag)
            .assertHasClickAction()
            .performClick()

        // Wait until navigation completes
        composeRule.waitUntilExactlyOneExists(
            hasTestTag("setupPlateauWidthTextField"),
            timeoutMillis = timeoutMillis,
        )

        // Input setup data
        composeRule.onNodeWithTag("setupPlateauWidthTextField")
            .performTextReplacement("5")
        composeRule.onNodeWithTag("setupPlateauHeightTextField")
            .performTextReplacement("5")
        composeRule.onNodeWithTag("setupInitialXTextField")
            .performTextReplacement("1")
        composeRule.onNodeWithTag("setupInitialYTextField")
            .performTextReplacement("2")
        composeRule.onNodeWithTag("setupInitialDirectionTextField")
            .performClick()

        // Wait until model sheet opens & select North
        val northButtonTestTag = "modalSheet${CardinalDirection.North}DirectionButton"
        composeRule.waitUntilExactlyOneExists(
            hasTestTag(northButtonTestTag),
            timeoutMillis = timeoutMillis,
        )
        composeRule.onNodeWithTag(northButtonTestTag, useUnmergedTree = true)
            .performClick()

        // Navigate to movements screen
        val continueButtonTestTag = "setupContinueButton"
        composeRule.waitUntilExactlyOneExists(
            hasTestTag(continueButtonTestTag),
            timeoutMillis = timeoutMillis,
        )
        composeRule.onNodeWithTag(continueButtonTestTag)
            .performClick()

        // Wait until navigation completes
        composeRule.waitUntilExactlyOneExists(
            hasTestTag("movementsTextField"),
            timeoutMillis = timeoutMillis,
        )

        // Input movements
        composeRule.onNodeWithTag("add${Movement.RotateLeft}MovementButton")
            .performClick()
        composeRule.onNodeWithTag("add${Movement.MoveForward}MovementButton")
            .performClick()
        composeRule.onNodeWithTag("add${Movement.RotateLeft}MovementButton")
            .performClick()
        composeRule.onNodeWithTag("add${Movement.MoveForward}MovementButton")
            .performClick()
        composeRule.onNodeWithTag("add${Movement.RotateLeft}MovementButton")
            .performClick()
        composeRule.onNodeWithTag("add${Movement.MoveForward}MovementButton")
            .performClick()
        composeRule.onNodeWithTag("add${Movement.RotateLeft}MovementButton")
            .performClick()
        composeRule.onNodeWithTag("add${Movement.MoveForward}MovementButton")
            .performClick()
        composeRule.onNodeWithTag("add${Movement.MoveForward}MovementButton")
            .performClick()

        // Assert correct movement sequence is shown
        composeRule.onNodeWithTag("movementsTextField", useUnmergedTree = true)
            .assertTextEquals("LMLMLMLMM")

        // Send instructions
        composeRule.onNodeWithTag("sendMovementsButton")
            .performClick()

        // Assert correct output in alert dialog.
        val outputTextTesTag = "movementsOutputDialogOutputText"
        composeRule.waitUntilExactlyOneExists(
            hasTestTag(outputTextTesTag),
            timeoutMillis = timeoutMillis,
        )
        composeRule.onNodeWithTag(outputTextTesTag)
            .assertTextEquals("1 3 N")
    }
}