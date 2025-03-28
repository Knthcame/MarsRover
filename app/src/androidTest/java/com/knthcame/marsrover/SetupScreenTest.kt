package com.knthcame.marsrover

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import com.knthcame.marsrover.data.control.models.CardinalDirection
import com.knthcame.marsrover.data.control.models.Coordinates
import com.knthcame.marsrover.ui.setup.SetupScreenRoute
import com.knthcame.marsrover.ui.theme.MarsRoverTheme
import org.junit.Rule
import org.junit.Test
import org.koin.compose.KoinApplication

class SetupScreenTest {
    @get:Rule
    val composeRule = createComposeRule()

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun continueButton_sendsInputData_toNextScreen() {
        var navTopRightCorner: Coordinates? = null
        var navInitialPosition: Coordinates? = null
        var navInitialDirection: CardinalDirection? = null

        composeRule.setContent {
            KoinApplication(application = {
                modules(androidModule)
            }) {
                MarsRoverTheme {
                    SetupScreenRoute(onSetupCompleted = { topRightCorner, initialPosition, initialDirection ->
                        navTopRightCorner = topRightCorner
                        navInitialPosition = initialPosition
                        navInitialDirection = initialDirection
                    })

                }
            }
        }

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

        // Wait until model sheet opens & select North
        val northButtonTestTag = "modalSheet${CardinalDirection.North}DirectionButton"
        composeRule.waitUntilExactlyOneExists(hasTestTag(northButtonTestTag))
        composeRule.onNodeWithTag(northButtonTestTag, useUnmergedTree = true)
            .performClick()

        // Navigate to movements screen
        val continueButtonTestTag = "setupContinueButton"
        composeRule.waitUntilExactlyOneExists(hasTestTag(continueButtonTestTag))
        composeRule.onNodeWithTag(continueButtonTestTag)
            .assertHasClickAction()
            .performClick()

        // Wait until navigation completes
        assert(navTopRightCorner == Coordinates(5, 5))
        assert(navInitialPosition == Coordinates(1, 2))
        assert(navInitialDirection == CardinalDirection.North)
    }
}