package com.knthcame.marsrover.ui.setup

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextReplacement
import com.knthcame.marsrover.HiltTestActivity
import com.knthcame.marsrover.data.control.models.CardinalDirection
import com.knthcame.marsrover.ui.SETUP_CONTINUE_BUTTON_TAG
import com.knthcame.marsrover.ui.SETUP_INITIAL_DIRECTION_TAG
import com.knthcame.marsrover.ui.SETUP_INITIAL_X_TAG
import com.knthcame.marsrover.ui.SETUP_INITIAL_Y_TAG
import com.knthcame.marsrover.ui.SETUP_PLATEAU_HEIGHT_TAG
import com.knthcame.marsrover.ui.SETUP_PLATEAU_WIDTH_TAG
import com.knthcame.marsrover.ui.modalSheetDirectionButtonTag
import com.knthcame.marsrover.ui.theme.MarsRoverTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
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

        composeRule.setContent {
            MarsRoverTheme {
                SetupScreenRoute(onNavigate = { })
            }
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun screenState_isUpdated_onValidUserInput() {
        // Input text fields
        val widthTextField = composeRule.onNodeWithTag(SETUP_PLATEAU_WIDTH_TAG)
        widthTextField.performTextReplacement("7")
        widthTextField.assertTextContains("7")

        val heightTextField = composeRule.onNodeWithTag(SETUP_PLATEAU_HEIGHT_TAG)
        heightTextField.performTextReplacement("3")
        heightTextField.assertTextContains("3")

        val xTextField = composeRule.onNodeWithTag(SETUP_INITIAL_X_TAG)
        xTextField.performTextReplacement("1")
        xTextField.assertTextContains("1")

        val yTextField = composeRule.onNodeWithTag(SETUP_INITIAL_Y_TAG)
        yTextField.performTextReplacement("2")
        yTextField.assertTextContains("2")

        // Select direction
        composeRule.onNodeWithTag(SETUP_INITIAL_DIRECTION_TAG)
            .performClick()
        val eastButtonTestTag = modalSheetDirectionButtonTag(CardinalDirection.East)
        composeRule.waitUntilExactlyOneExists(hasTestTag(eastButtonTestTag))
        composeRule.onNodeWithTag(eastButtonTestTag, useUnmergedTree = true)
            .performClick()
        composeRule.waitUntilDoesNotExist(hasTestTag(eastButtonTestTag))

        composeRule.onNodeWithTag(SETUP_INITIAL_DIRECTION_TAG)
            .assertTextContains(CardinalDirection.East.name)
    }

    @Test
    fun screenState_isNotUpdated_onInvalidUserInput() {
        val widthTextField = composeRule.onNodeWithTag(SETUP_PLATEAU_WIDTH_TAG)
        widthTextField.performTextReplacement("a")
        widthTextField.assertTextContains("5")

        val heightTextField = composeRule.onNodeWithTag(SETUP_PLATEAU_HEIGHT_TAG)
        heightTextField.performTextReplacement(".")
        heightTextField.assertTextContains("5")

        val xTextField = composeRule.onNodeWithTag(SETUP_INITIAL_X_TAG)
        xTextField.performTextReplacement("~")
        xTextField.assertTextContains("0")

        val yTextField = composeRule.onNodeWithTag(SETUP_INITIAL_Y_TAG)
        yTextField.performTextReplacement("!")
        yTextField.assertTextContains("0")

        val directionSelector = composeRule.onNodeWithTag(SETUP_INITIAL_DIRECTION_TAG)
        directionSelector.assert(
            matcher = SemanticsMatcher.expectValue(
                SemanticsProperties.IsEditable,
                false,
            ),
        )
    }

    @Test
    fun continueButton_isDisabled_onEmptyPlateauWidth() {
        composeRule.onNodeWithTag(SETUP_PLATEAU_WIDTH_TAG)
            .performTextClearance()

        composeRule.onNodeWithTag(SETUP_CONTINUE_BUTTON_TAG)
            .assertIsNotEnabled()
    }

    @Test
    fun continueButton_isDisabled_onEmptyPlateauHeight() {
        composeRule.onNodeWithTag(SETUP_PLATEAU_HEIGHT_TAG)
            .performTextClearance()

        composeRule.onNodeWithTag(SETUP_CONTINUE_BUTTON_TAG)
            .assertIsNotEnabled()
    }

    @Test
    fun continueButton_isDisabled_onEmptyInitialX() {
        composeRule.onNodeWithTag(SETUP_INITIAL_X_TAG)
            .performTextClearance()

        composeRule.onNodeWithTag(SETUP_CONTINUE_BUTTON_TAG)
            .assertIsNotEnabled()
    }

    @Test
    fun continueButton_isDisabled_onEmptyInitialY() {
        composeRule.onNodeWithTag(SETUP_INITIAL_Y_TAG)
            .performTextClearance()

        composeRule.onNodeWithTag(SETUP_CONTINUE_BUTTON_TAG)
            .assertIsNotEnabled()
    }
}
