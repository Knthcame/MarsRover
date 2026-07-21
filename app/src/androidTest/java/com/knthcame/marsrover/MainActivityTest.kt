package com.knthcame.marsrover

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextReplacement
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.knthcame.marsrover.ui.HOME_START_BUTTON_TAG
import com.knthcame.marsrover.ui.HOME_TOP_BAR_TITLE_TAG
import com.knthcame.marsrover.ui.MOVEMENTS_OUTPUT_DIALOG_OUTPUT_TEXT_TAG
import com.knthcame.marsrover.ui.MOVEMENTS_TEXT_FIELD_TAG
import com.knthcame.marsrover.ui.SEND_MOVEMENTS_BUTTON_TAG
import com.knthcame.marsrover.ui.SETUP_CONTINUE_BUTTON_TAG
import com.knthcame.marsrover.ui.SETUP_INITIAL_X_TAG
import com.knthcame.marsrover.ui.SETUP_INITIAL_Y_TAG
import com.knthcame.marsrover.ui.SETUP_PLATEAU_HEIGHT_TAG
import com.knthcame.marsrover.ui.SETUP_PLATEAU_WIDTH_TAG
import com.knthcame.marsrover.ui.addMovementButtonTag
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
        composeActivityRule.onNodeWithTag(HOME_TOP_BAR_TITLE_TAG, useUnmergedTree = true)
            .assertIsDisplayed()
    }

    @Test
    fun endToEndTest() {
        // Navigate to setup screen
        composeActivityRule.onNodeWithTag(HOME_START_BUTTON_TAG).performClick()

        // Input setup data
        composeActivityRule.onNodeWithTag(SETUP_PLATEAU_WIDTH_TAG).performTextReplacement("5")
        composeActivityRule.onNodeWithTag(SETUP_PLATEAU_HEIGHT_TAG).performTextReplacement("3")
        composeActivityRule.onNodeWithTag(SETUP_INITIAL_X_TAG).performTextReplacement("1")
        composeActivityRule.onNodeWithTag(SETUP_INITIAL_Y_TAG).performTextReplacement("2")

        // Navigate to movements screen
        composeActivityRule.onNodeWithTag(SETUP_CONTINUE_BUTTON_TAG, useUnmergedTree = true)
            .performScrollTo().performClick()

        // Input movements
        composeActivityRule.onNodeWithTag(
            testTag = addMovementButtonTag(Movement.RotateLeft),
            useUnmergedTree = true,
        ).performScrollTo().performClick()
        composeActivityRule.onNodeWithTag(
            testTag = addMovementButtonTag(Movement.MoveForward),
            useUnmergedTree = true,
        ).performScrollTo().performClick()
        composeActivityRule.onNodeWithTag(
            testTag = addMovementButtonTag(Movement.RotateLeft),
            useUnmergedTree = true,
        ).performScrollTo().performClick()
        composeActivityRule.onNodeWithTag(
            testTag = addMovementButtonTag(Movement.MoveForward),
            useUnmergedTree = true,
        ).performScrollTo().performClick()
        composeActivityRule.onNodeWithTag(
            testTag = addMovementButtonTag(Movement.RotateLeft),
            useUnmergedTree = true,
        ).performScrollTo().performClick()
        composeActivityRule.onNodeWithTag(
            testTag = addMovementButtonTag(Movement.MoveForward),
            useUnmergedTree = true,
        ).performScrollTo().performClick()
        composeActivityRule.onNodeWithTag(
            testTag = addMovementButtonTag(Movement.RotateLeft),
            useUnmergedTree = true,
        ).performScrollTo().performClick()
        composeActivityRule.onNodeWithTag(
            testTag = addMovementButtonTag(Movement.MoveForward),
            useUnmergedTree = true,
        ).performScrollTo().performClick()
        composeActivityRule.onNodeWithTag(
            testTag = addMovementButtonTag(Movement.MoveForward),
            useUnmergedTree = true,
        ).performScrollTo().performClick()

        // Assert correct movement sequence is shown
        composeActivityRule.onNodeWithTag(MOVEMENTS_TEXT_FIELD_TAG, useUnmergedTree = true)
            .assertTextEquals("LMLMLMLMM")

        // Send instructions
        composeActivityRule.onNodeWithTag(SEND_MOVEMENTS_BUTTON_TAG, useUnmergedTree = true)
            .performScrollTo().performClick()

        // Assert correct output in alert dialog.
        composeActivityRule.onNodeWithTag(
            MOVEMENTS_OUTPUT_DIALOG_OUTPUT_TEXT_TAG,
            useUnmergedTree = true,
        )
            .assertTextEquals("1 3 N")
    }
}
