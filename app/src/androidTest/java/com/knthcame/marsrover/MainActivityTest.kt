package com.knthcame.marsrover

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.knthcame.marsrover.data.control.models.CardinalDirection
import com.knthcame.marsrover.ui.movements.Movement
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    val composeActivityRule = createAndroidComposeRule<MainActivity>()

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun endToEndTest() {
        val timeoutMillis = 10_000L
        // Navigate to setup screen
        val startButtonTestTag = "homeStartButton"
        composeActivityRule.waitUntilExactlyOneExists(
            hasTestTag(startButtonTestTag),
            timeoutMillis = timeoutMillis,
        )
        composeActivityRule.onNodeWithTag(startButtonTestTag)
            .performClick()

        // Wait until navigation completes
        composeActivityRule.waitUntilExactlyOneExists(
            hasTestTag("setupPlateauWidthTextField"),
            timeoutMillis = timeoutMillis,
        )

        // Input setup data
        composeActivityRule.onNodeWithTag("setupPlateauWidthTextField")
            .performTextReplacement("5")
        composeActivityRule.onNodeWithTag("setupPlateauHeightTextField")
            .performTextReplacement("5")
        composeActivityRule.onNodeWithTag("setupInitialXTextField")
            .performTextReplacement("1")
        composeActivityRule.onNodeWithTag("setupInitialYTextField")
            .performTextReplacement("2")
        composeActivityRule.onNodeWithTag("setupInitialDirectionTextField")
            .performClick()

        // Wait until model sheet opens & select North
        val northButtonTestTag = "modalSheet${CardinalDirection.North}DirectionButton"
        composeActivityRule.waitUntilExactlyOneExists(
            hasTestTag(northButtonTestTag),
            timeoutMillis = timeoutMillis,
        )
        composeActivityRule.onNodeWithTag(northButtonTestTag, useUnmergedTree = true)
            .performClick()

        // Navigate to movements screen
        val continueButtonTestTag = "setupContinueButton"
        composeActivityRule.waitUntilExactlyOneExists(
            hasTestTag(continueButtonTestTag),
            timeoutMillis = timeoutMillis,
        )
        composeActivityRule.onNodeWithTag(continueButtonTestTag)
            .performClick()

        // Wait until navigation completes
        composeActivityRule.waitUntilExactlyOneExists(
            hasTestTag("movementsTextField"),
            timeoutMillis = timeoutMillis,
        )

        // Input movements
        composeActivityRule.onNodeWithTag("add${Movement.RotateLeft}MovementButton")
            .performClick()
        composeActivityRule.onNodeWithTag("add${Movement.MoveForward}MovementButton")
            .performClick()
        composeActivityRule.onNodeWithTag("add${Movement.RotateLeft}MovementButton")
            .performClick()
        composeActivityRule.onNodeWithTag("add${Movement.MoveForward}MovementButton")
            .performClick()
        composeActivityRule.onNodeWithTag("add${Movement.RotateLeft}MovementButton")
            .performClick()
        composeActivityRule.onNodeWithTag("add${Movement.MoveForward}MovementButton")
            .performClick()
        composeActivityRule.onNodeWithTag("add${Movement.RotateLeft}MovementButton")
            .performClick()
        composeActivityRule.onNodeWithTag("add${Movement.MoveForward}MovementButton")
            .performClick()
        composeActivityRule.onNodeWithTag("add${Movement.MoveForward}MovementButton")
            .performClick()

        // Assert correct movement sequence is shown
        composeActivityRule.onNodeWithTag("movementsTextField", useUnmergedTree = true)
            .assertTextEquals("LMLMLMLMM")

        // Send instructions
        composeActivityRule.onNodeWithTag("sendMovementsButton")
            .performClick()

        // Assert correct output in alert dialog.
        val outputTextTesTag = "movementsOutputDialogOutputText"
        composeActivityRule.waitUntilExactlyOneExists(
            hasTestTag(outputTextTesTag),
            timeoutMillis = timeoutMillis,
        )
        composeActivityRule.onNodeWithTag(outputTextTesTag)
            .assertTextEquals("1 3 N")
    }
}