package com.knthcame.marsrover.ui.movements

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.lifecycle.SavedStateHandle
import com.knthcame.marsrover.HiltTestActivity
import com.knthcame.marsrover.data.calculation.RoverPositionCalculator
import com.knthcame.marsrover.data.control.models.CardinalDirection
import com.knthcame.marsrover.data.control.repositories.RoverRepository
import com.knthcame.marsrover.testViewModelScope
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class MovementsScreenTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<HiltTestActivity>()

    @Inject
    lateinit var roverRepository: RoverRepository

    @Inject
    lateinit var roverPositionCalculator: RoverPositionCalculator

    @Before
    fun before() {
        hiltRule.inject()
    }

    @Test
    fun movementsTextField_displaysMovementsSequence_accordingToUserInput() {
        val savedStateHandle = SavedStateHandle(
            mapOf(
                "plateauHeight" to 5,
                "plateauWidth" to 5,
                "initialPositionX" to 1,
                "initialPositionY" to 2,
                "initialDirection" to CardinalDirection.North,
            )
        )
        val vm = MovementsViewModel(
            savedStateHandle = savedStateHandle,
            roverRepository = roverRepository,
            roverPositionCalculator = roverPositionCalculator,
            viewModeScope = testViewModelScope,
        )

        composeRule.setContent {
            MovementsScreenRoute(
                onNavigateBack = { },
                viewModel = vm,
            )
        }

        // Input movements
        composeRule.onNodeWithTag("add${Movement.RotateLeft}MovementButton")
            .performScrollTo()
            .performClick()
        composeRule.onNodeWithTag("add${Movement.MoveForward}MovementButton")
            .performScrollTo()
            .performClick()
        composeRule.onNodeWithTag("add${Movement.RotateLeft}MovementButton")
            .performScrollTo()
            .performClick()
        composeRule.onNodeWithTag("add${Movement.MoveForward}MovementButton")
            .performScrollTo()
            .performClick()
        composeRule.onNodeWithTag("add${Movement.RotateLeft}MovementButton")
            .performScrollTo()
            .performClick()
        composeRule.onNodeWithTag("add${Movement.MoveForward}MovementButton")
            .performScrollTo()
            .performClick()
        composeRule.onNodeWithTag("add${Movement.RotateLeft}MovementButton")
            .performScrollTo()
            .performClick()
        composeRule.onNodeWithTag("add${Movement.MoveForward}MovementButton")
            .performScrollTo()
            .performClick()
        composeRule.onNodeWithTag("add${Movement.MoveForward}MovementButton")
            .performScrollTo()
            .performClick()

        // Assert correct movement sequence is shown
        composeRule.onNodeWithTag("movementsTextField", useUnmergedTree = true)
            .assertTextEquals("LMLMLMLMM")

        // Send instructions
        composeRule.onNodeWithTag("sendMovementsButton", useUnmergedTree = true)
            .performScrollTo()
            .performClick()

        // Assert correct output in alert dialog.
        composeRule.onNodeWithTag("movementsOutputDialogOutputText", useUnmergedTree = true)
            .assertTextEquals("1 3 N")
    }
}