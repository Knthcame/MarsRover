package com.knthcame.marsrover

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextReplacement
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
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

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun endToEndTest() {
        val timeoutMillis = 10_000L
        // Navigate to setup screen
        composeActivityRule.onNodeWithTag("homeStartButton")
            .performClick()

        composeActivityRule.waitUntilAtLeastOneExists(
            matcher = hasTestTag("setupPlateauWidthTextField"),
            timeoutMillis = timeoutMillis,
        )

        // Input setup data
        composeActivityRule.onNodeWithTag("setupPlateauWidthTextField")
            .performTextReplacement("5")
        composeActivityRule.onNodeWithTag("setupPlateauHeightTextField")
            .performTextReplacement("3")
        composeActivityRule.onNodeWithTag("setupInitialXTextField")
            .performTextReplacement("1")
        composeActivityRule.onNodeWithTag("setupInitialYTextField")
            .performTextReplacement("2")

        // Navigate to movements screen
        composeActivityRule.onNodeWithTag("setupContinueButton")
            .performScrollTo()
            .performClick()

        composeActivityRule.waitUntilAtLeastOneExists(
            matcher = hasTestTag("movementsTopBarTitle"),
            timeoutMillis = timeoutMillis,
        )

        // Input movements
        composeActivityRule.onNodeWithTag("add${Movement.RotateLeft}MovementButton")
            .performScrollTo()
            .performClick()
        composeActivityRule.onNodeWithTag("add${Movement.MoveForward}MovementButton")
            .performScrollTo()
            .performClick()
        composeActivityRule.onNodeWithTag("add${Movement.RotateLeft}MovementButton")
            .performScrollTo()
            .performClick()
        composeActivityRule.onNodeWithTag("add${Movement.MoveForward}MovementButton")
            .performScrollTo()
            .performClick()
        composeActivityRule.onNodeWithTag("add${Movement.RotateLeft}MovementButton")
            .performScrollTo()
            .performClick()
        composeActivityRule.onNodeWithTag("add${Movement.MoveForward}MovementButton")
            .performScrollTo()
            .performClick()
        composeActivityRule.onNodeWithTag("add${Movement.RotateLeft}MovementButton")
            .performScrollTo()
            .performClick()
        composeActivityRule.onNodeWithTag("add${Movement.MoveForward}MovementButton")
            .performScrollTo()
            .performClick()
        composeActivityRule.onNodeWithTag("add${Movement.MoveForward}MovementButton")
            .performScrollTo()
            .performClick()

        // Assert correct movement sequence is shown
        composeActivityRule.onNodeWithTag("movementsTextField", useUnmergedTree = true)
            .assertTextEquals("LMLMLMLMM")

        // Send instructions
        composeActivityRule.onNodeWithTag("sendMovementsButton")
            .performScrollTo()
            .performClick()

        // Assert correct output in alert dialog.
        val outputTextTestTag = "movementsOutputDialogOutputText"
        composeActivityRule.waitUntilAtLeastOneExists(
            hasTestTag(outputTextTestTag),
            timeoutMillis = 5_000,
        )
        composeActivityRule.onNodeWithTag(outputTextTestTag)
            .assertTextEquals("1 3 N")

    }
}