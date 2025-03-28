package com.knthcame.marsrover

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.knthcame.marsrover.data.control.models.CardinalDirection
import com.knthcame.marsrover.ui.MarsRoverNavHost
import com.knthcame.marsrover.ui.movements.Movement
import com.knthcame.marsrover.ui.theme.MarsRoverTheme
import org.junit.Rule
import org.junit.runner.RunWith
import org.koin.compose.KoinApplication

@RunWith(AndroidJUnit4::class)
class MarsRoverTest {
    @get:Rule
    val composeRule = createComposeRule()

    //@Test // Does not work on the CI workflow. Uncomment to execute locally.
    fun endToEndTest() {
        composeRule.setContent {
            KoinApplication(application = {
                modules(androidModule)
            }) {
                MarsRoverTheme {
                    MarsRoverNavHost(rememberNavController())
                }
            }
        }

        // Navigate to setup screen
        composeRule.onNodeWithTag("homeStartButton")
            .performClick()

        // Input setup data
        composeRule.onNodeWithTag("setupPlateauWidthTextField")
            .performTextReplacement("5")
        composeRule.onNodeWithTag("setupPlateauHeightTextField")
            .performTextReplacement("5")
        composeRule.onNodeWithTag("setupInitialXTextField")
            .performTextReplacement("1")
        composeRule.onNodeWithTag("setupInitialYTextField")
            .performTextReplacement("2")
        composeRule.onNodeWithTag("setupInitialDirectionTextField")
            .performClick()
        composeRule.onNodeWithTag(
            "modalSheet${CardinalDirection.North}DirectionButton",
            useUnmergedTree = true,
        )
            .performClick()

        // Navigate to movements screen
        composeRule.onNodeWithTag("setupContinueButton")
            .performClick()

        // Input movements
        composeRule.onNodeWithTag("add${Movement.RotateLeft}MovementButton")
            .performClick()
        composeRule.onNodeWithTag("add${Movement.MoveForward}MovementButton")
            .performClick()
        composeRule.onNodeWithTag("add${Movement.RotateLeft}MovementButton")
            .performClick()
        composeRule.onNodeWithTag("add${Movement.MoveForward}MovementButton")
            .performClick()
        composeRule.onNodeWithTag("add${Movement.RotateLeft}MovementButton")
            .performClick()
        composeRule.onNodeWithTag("add${Movement.MoveForward}MovementButton")
            .performClick()
        composeRule.onNodeWithTag("add${Movement.RotateLeft}MovementButton")
            .performClick()
        composeRule.onNodeWithTag("add${Movement.MoveForward}MovementButton")
            .performClick()
        composeRule.onNodeWithTag("add${Movement.MoveForward}MovementButton")
            .performClick()

        // Assert correct movement sequence is shown
        composeRule.onNodeWithTag("movementsTextField", useUnmergedTree = true)
            .assertTextEquals("LMLMLMLMM")

        // Send instructions
        composeRule.onNodeWithTag("sendMovementsButton")
            .performClick()

        // Assert correct output in alert dialog.
        composeRule.onNodeWithTag("movementsOutputDialogOutputText")
            .assertTextEquals("1 3 N")
    }
}