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
import com.knthcame.marsrover.ui.SetupContinueButtonTag
import com.knthcame.marsrover.ui.SetupInitialDirectionTag
import com.knthcame.marsrover.ui.SetupInitialXTag
import com.knthcame.marsrover.ui.SetupInitialYTag
import com.knthcame.marsrover.ui.SetupPlateauHeightTag
import com.knthcame.marsrover.ui.SetupPlateauWidthTag
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
        val widthTextField = composeRule.onNodeWithTag(SetupPlateauWidthTag)
        widthTextField.performTextReplacement("7")
        widthTextField.assertTextContains("7")

        val heightTextField = composeRule.onNodeWithTag(SetupPlateauHeightTag)
        heightTextField.performTextReplacement("3")
        heightTextField.assertTextContains("3")

        val xTextField = composeRule.onNodeWithTag(SetupInitialXTag)
        xTextField.performTextReplacement("1")
        xTextField.assertTextContains("1")

        val yTextField = composeRule.onNodeWithTag(SetupInitialYTag)
        yTextField.performTextReplacement("2")
        yTextField.assertTextContains("2")

        // Select direction
        composeRule.onNodeWithTag(SetupInitialDirectionTag)
            .performClick()
        val eastButtonTestTag = modalSheetDirectionButtonTag(CardinalDirection.East)
        composeRule.waitUntilExactlyOneExists(hasTestTag(eastButtonTestTag))
        composeRule.onNodeWithTag(eastButtonTestTag, useUnmergedTree = true)
            .performClick()
        composeRule.waitUntilDoesNotExist(hasTestTag(eastButtonTestTag))

        composeRule.onNodeWithTag(SetupInitialDirectionTag)
            .assertTextContains(CardinalDirection.East.name)
    }

    @Test
    fun screenState_isNotUpdated_onInvalidUserInput() {
        val widthTextField = composeRule.onNodeWithTag(SetupPlateauWidthTag)
        widthTextField.performTextReplacement("a")
        widthTextField.assertTextContains("5")

        val heightTextField = composeRule.onNodeWithTag(SetupPlateauHeightTag)
        heightTextField.performTextReplacement(".")
        heightTextField.assertTextContains("5")

        val xTextField = composeRule.onNodeWithTag(SetupInitialXTag)
        xTextField.performTextReplacement("~")
        xTextField.assertTextContains("0")

        val yTextField = composeRule.onNodeWithTag(SetupInitialYTag)
        yTextField.performTextReplacement("!")
        yTextField.assertTextContains("0")

        val directionSelector = composeRule.onNodeWithTag(SetupInitialDirectionTag)
        directionSelector.assert(
            matcher = SemanticsMatcher.expectValue(
                SemanticsProperties.IsEditable,
                false,
            ),
        )
    }

    @Test
    fun continueButton_isDisabled_onEmptyPlateauWidth() {
        composeRule.onNodeWithTag(SetupPlateauWidthTag)
            .performTextClearance()

        composeRule.onNodeWithTag(SetupContinueButtonTag)
            .assertIsNotEnabled()
    }

    @Test
    fun continueButton_isDisabled_onEmptyPlateauHeight() {
        composeRule.onNodeWithTag(SetupPlateauHeightTag)
            .performTextClearance()

        composeRule.onNodeWithTag(SetupContinueButtonTag)
            .assertIsNotEnabled()
    }

    @Test
    fun continueButton_isDisabled_onEmptyInitialX() {
        composeRule.onNodeWithTag(SetupInitialXTag)
            .performTextClearance()

        composeRule.onNodeWithTag(SetupContinueButtonTag)
            .assertIsNotEnabled()
    }

    @Test
    fun continueButton_isDisabled_onEmptyInitialY() {
        composeRule.onNodeWithTag(SetupInitialYTag)
            .performTextClearance()

        composeRule.onNodeWithTag(SetupContinueButtonTag)
            .assertIsNotEnabled()
    }
}
