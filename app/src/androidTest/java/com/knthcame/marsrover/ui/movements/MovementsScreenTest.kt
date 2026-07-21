package com.knthcame.marsrover.ui.movements

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.text.AnnotatedString
import com.knthcame.marsrover.HiltTestActivity
import com.knthcame.marsrover.data.control.models.CardinalDirection
import com.knthcame.marsrover.ui.MOVEMENTS_OUTPUT_DIALOG_OUTPUT_TEXT_TAG
import com.knthcame.marsrover.ui.MOVEMENTS_TEXT_FIELD_TAG
import com.knthcame.marsrover.ui.MOVEMENTS_TEXT_FIELD_TRAILING_ICON_TAG
import com.knthcame.marsrover.ui.SEND_MOVEMENTS_BUTTON_TAG
import com.knthcame.marsrover.ui.addMovementButtonTag
import com.knthcame.marsrover.ui.navigation.Movements
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MovementsScreenTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<HiltTestActivity>()

    @Before
    fun before() {
        hiltRule.inject()

        val movements = Movements(
            plateauHeight = 5,
            plateauWidth = 5,
            initialPositionX = 1,
            initialPositionY = 2,
            initialDirection = CardinalDirection.North,
        )

        composeRule.setContent {
            MovementsScreenRoute(
                movements = movements,
                onNavigateBack = { },
            )
        }
    }

    @Test
    fun movementsTextField_displaysMovementsSequence_accordingToUserInput() {
        // Input movements
        composeRule.onNodeWithTag(addMovementButtonTag(Movement.RotateLeft))
            .performScrollTo()
            .performClick()
        composeRule.onNodeWithTag(addMovementButtonTag(Movement.MoveForward))
            .performScrollTo()
            .performClick()
        composeRule.onNodeWithTag(addMovementButtonTag(Movement.RotateLeft))
            .performScrollTo()
            .performClick()
        composeRule.onNodeWithTag(addMovementButtonTag(Movement.MoveForward))
            .performScrollTo()
            .performClick()
        composeRule.onNodeWithTag(addMovementButtonTag(Movement.RotateLeft))
            .performScrollTo()
            .performClick()
        composeRule.onNodeWithTag(addMovementButtonTag(Movement.MoveForward))
            .performScrollTo()
            .performClick()
        composeRule.onNodeWithTag(addMovementButtonTag(Movement.RotateLeft))
            .performScrollTo()
            .performClick()
        composeRule.onNodeWithTag(addMovementButtonTag(Movement.MoveForward))
            .performScrollTo()
            .performClick()
        composeRule.onNodeWithTag(addMovementButtonTag(Movement.MoveForward))
            .performScrollTo()
            .performClick()

        // Add & remove RotateRight movement
        composeRule.onNodeWithTag(addMovementButtonTag(Movement.RotateRight))
            .performScrollTo()
            .performClick()
        composeRule.onNodeWithTag(MOVEMENTS_TEXT_FIELD_TRAILING_ICON_TAG)
            .performScrollTo()
            .performClick()

        // Assert correct movement sequence is shown
        composeRule.onNodeWithTag(MOVEMENTS_TEXT_FIELD_TAG, useUnmergedTree = true)
            .assertTextEquals("LMLMLMLMM")

        // Send instructions
        composeRule.onNodeWithTag(SEND_MOVEMENTS_BUTTON_TAG, useUnmergedTree = true)
            .performScrollTo()
            .performClick()

        // Assert correct output in alert dialog.
        composeRule.onNodeWithTag(MOVEMENTS_OUTPUT_DIALOG_OUTPUT_TEXT_TAG, useUnmergedTree = true)
            .assertTextEquals("1 3 N")
    }

    @Test
    fun sendMovementsButton_isDisabled_onEmptyMovementsTextField() {
        composeRule.onNodeWithTag(MOVEMENTS_TEXT_FIELD_TAG)
            .assert(
                SemanticsMatcher.expectValue(
                    SemanticsProperties.InputText,
                    AnnotatedString(""),
                ),
            )

        composeRule.onNodeWithTag(SEND_MOVEMENTS_BUTTON_TAG)
            .assertIsNotEnabled()
    }
}
