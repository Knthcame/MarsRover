package com.knthcame.marsrover

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextReplacement
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.knthcame.marsrover.ui.movements.Movement
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MainActivityTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeActivityRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun before() {
        hiltRule.inject()
    }

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
        composeActivityRule.onNodeWithTag("homeStartButton").performClick()

        // Input setup data
        composeActivityRule.onNodeWithTag("setupPlateauWidthTextField").performTextReplacement("5")
        composeActivityRule.onNodeWithTag("setupPlateauHeightTextField").performTextReplacement("3")
        composeActivityRule.onNodeWithTag("setupInitialXTextField").performTextReplacement("1")
        composeActivityRule.onNodeWithTag("setupInitialYTextField").performTextReplacement("2")

        // Navigate to movements screen
        composeActivityRule.onNodeWithTag("setupContinueButton", useUnmergedTree = true)
            .performScrollTo().performClick()

        // Input movements
        composeActivityRule.onNodeWithTag(
            testTag = "add${Movement.RotateLeft}MovementButton",
            useUnmergedTree = true,
        ).performScrollTo().performClick()
        composeActivityRule.onNodeWithTag(
            testTag = "add${Movement.MoveForward}MovementButton",
            useUnmergedTree = true,
        ).performScrollTo().performClick()
        composeActivityRule.onNodeWithTag(
            testTag = "add${Movement.RotateLeft}MovementButton",
            useUnmergedTree = true,
        ).performScrollTo().performClick()
        composeActivityRule.onNodeWithTag(
            testTag = "add${Movement.MoveForward}MovementButton",
            useUnmergedTree = true,
        ).performScrollTo().performClick()
        composeActivityRule.onNodeWithTag(
            testTag = "add${Movement.RotateLeft}MovementButton",
            useUnmergedTree = true,
        ).performScrollTo().performClick()
        composeActivityRule.onNodeWithTag(
            testTag = "add${Movement.MoveForward}MovementButton",
            useUnmergedTree = true,
        ).performScrollTo().performClick()
        composeActivityRule.onNodeWithTag(
            testTag = "add${Movement.RotateLeft}MovementButton",
            useUnmergedTree = true,
        ).performScrollTo().performClick()
        composeActivityRule.onNodeWithTag(
            testTag = "add${Movement.MoveForward}MovementButton",
            useUnmergedTree = true,
        ).performScrollTo().performClick()
        composeActivityRule.onNodeWithTag(
            testTag = "add${Movement.MoveForward}MovementButton",
            useUnmergedTree = true,
        ).performScrollTo().performClick()

        // Assert correct movement sequence is shown
        composeActivityRule.onNodeWithTag("movementsTextField", useUnmergedTree = true)
            .assertTextEquals("LMLMLMLMM")

        // Send instructions
        composeActivityRule.onNodeWithTag("sendMovementsButton", useUnmergedTree = true)
            .performScrollTo().performClick()

        // Assert correct output in alert dialog.
        composeActivityRule.onNodeWithTag("movementsOutputDialogOutputText", useUnmergedTree = true)
            .assertTextEquals("1 3 N")

    }
}