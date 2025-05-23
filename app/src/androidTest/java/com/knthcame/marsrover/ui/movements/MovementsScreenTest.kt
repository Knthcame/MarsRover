package com.knthcame.marsrover.ui.movements

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.lifecycle.SavedStateHandle
import com.knthcame.marsrover.androidModule
import com.knthcame.marsrover.data.control.models.CardinalDirection
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication
import org.koin.dsl.module

class MovementsScreenTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun movementsTextField_displaysMovementsSequence_accordingToUserInput() {
        val testModule = module {
            single {
                SavedStateHandle(
                    mapOf(
                        "plateauHeight" to 5,
                        "plateauWidth" to 5,
                        "initialPositionX" to 1,
                        "initialPositionY" to 2,
                        "initialDirection" to CardinalDirection.North,
                    )
                )
            }
        }

        composeRule.setContent {
            KoinApplication(application = { modules(androidModule, testModule) }) {
                MovementsScreenRoute(
                    onNavigateBack = { },
                    viewModel = koinViewModel<MovementsViewModel>(),
                )
            }
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