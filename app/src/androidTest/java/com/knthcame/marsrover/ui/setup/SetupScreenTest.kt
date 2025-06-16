package com.knthcame.marsrover.ui.setup

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextReplacement
import com.knthcame.marsrover.HiltTestActivity
import com.knthcame.marsrover.data.control.models.CardinalDirection
import com.knthcame.marsrover.data.control.models.Coordinates
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
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun continueButton_sendsInputData_toNextScreen() {
        var navTopRightCorner: Coordinates? = null
        var navInitialPosition: Coordinates? = null
        var navInitialDirection: CardinalDirection? = null

        composeRule.setContent {
            MarsRoverTheme {
                SetupScreenRoute(onSetupCompleted = { topRightCorner, initialPosition, initialDirection ->
                    navTopRightCorner = topRightCorner
                    navInitialPosition = initialPosition
                    navInitialDirection = initialDirection
                })
            }
        }

        // Input setup data
        composeRule.onNodeWithTag("setupPlateauWidthTextField")
            .performTextReplacement("5")
        composeRule.onNodeWithTag("setupPlateauHeightTextField")
            .performTextReplacement("2")
        composeRule.onNodeWithTag("setupInitialXTextField")
            .performTextReplacement("1")
        composeRule.onNodeWithTag("setupInitialYTextField")
            .performTextReplacement("2")
        composeRule.onNodeWithTag("setupInitialDirectionTextField")
            .performClick()

        val northButtonTestTag = "modalSheet${CardinalDirection.North}DirectionButton"
        composeRule.waitUntilExactlyOneExists(hasTestTag(northButtonTestTag))
        composeRule.onNodeWithTag(northButtonTestTag, useUnmergedTree = true)
            .performClick()
        composeRule.waitUntilDoesNotExist(hasTestTag(northButtonTestTag))

        // Click continue button
        composeRule.onNodeWithTag("setupContinueButton")
            .performScrollTo()
            .performClick()

        // Assert input is communicated to next screen.
        assert(navTopRightCorner == Coordinates(5, 2))
        assert(navInitialPosition == Coordinates(1, 2))
        assert(navInitialDirection == CardinalDirection.North)
    }
}