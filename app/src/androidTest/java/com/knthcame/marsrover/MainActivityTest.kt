package com.knthcame.marsrover

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.knthcame.marsrover.data.control.models.CardinalDirection
import com.knthcame.marsrover.ui.movements.Movement
import org.junit.Assert.assertEquals
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

    @Test
    fun testPackageName() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.knthcame.marsrover", appContext.packageName)
    }

    @Test
    fun testLaunch() {
        composeActivityRule.onNodeWithTag("homeTopBarTitle")
            .assertIsDisplayed()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun endToEndTest() {
        // Navigate to setup screen
        val startButtonTestTag = "homeStartButton"
        composeActivityRule.waitUntilExactlyOneExists(hasTestTag(startButtonTestTag))
        composeActivityRule.onNodeWithTag(startButtonTestTag)
            .performClick()

        // Wait until navigation completes
        composeActivityRule.waitUntilExactlyOneExists(hasTestTag("setupPlateauWidthTextField"))

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
        composeActivityRule.waitUntilExactlyOneExists(hasTestTag(northButtonTestTag))
        composeActivityRule.onNodeWithTag(northButtonTestTag, useUnmergedTree = true)
            .performClick()

        // Navigate to movements screen
        val continueButtonTestTag = "setupContinueButton"
        composeActivityRule.waitUntilExactlyOneExists(hasTestTag(continueButtonTestTag))
        composeActivityRule.onNodeWithTag(continueButtonTestTag)
            .performClick()

        // Wait until navigation completes
        composeActivityRule.waitUntilExactlyOneExists(hasTestTag("movementsTextField"))

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
        composeActivityRule.waitUntilExactlyOneExists(hasTestTag(outputTextTesTag))
        composeActivityRule.onNodeWithTag(outputTextTesTag)
            .assertTextEquals("1 3 N")
    }
}