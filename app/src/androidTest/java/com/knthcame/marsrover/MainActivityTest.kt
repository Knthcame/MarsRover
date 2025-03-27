package com.knthcame.marsrover

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
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
        composeActivityRule.onNodeWithTag("homeTopBarTitle", useUnmergedTree = true)
            .assertIsDisplayed()
    }

    @Test
    fun endToEndTest() {
        // Navigate to setup screen
        composeActivityRule.onNodeWithTag("homeStartButton", useUnmergedTree = true)
            .performClick()

        // Input setup data
        composeActivityRule.onNodeWithTag("setupPlateauWidthTextField", useUnmergedTree = true)
            .performTextReplacement("5")
        composeActivityRule.onNodeWithTag("setupPlateauHeightTextField", useUnmergedTree = true)
            .performTextReplacement("5")
        composeActivityRule.onNodeWithTag("setupInitialXTextField", useUnmergedTree = true)
            .performTextReplacement("1")
        composeActivityRule.onNodeWithTag("setupInitialYTextField", useUnmergedTree = true)
            .performTextReplacement("2")
        composeActivityRule.onNodeWithTag("setupInitialDirectionTextField", useUnmergedTree = true)
            .performClick()
        composeActivityRule.onNodeWithTag(
            "modalSheet${CardinalDirection.North}DirectionButton",
            useUnmergedTree = true,
        ).performClick()

        // Navigate to movements screen
        composeActivityRule.onNodeWithTag("setupContinueButton", useUnmergedTree = true)
            .performClick()

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
        composeActivityRule.onNodeWithTag("sendMovementsButton", useUnmergedTree = true)
            .performClick()

        // Assert correct output in alert dialog.
        composeActivityRule.onNodeWithTag("movementsOutputDialogOutputText", useUnmergedTree = true)
            .assertTextEquals("1 3 N")
    }
}