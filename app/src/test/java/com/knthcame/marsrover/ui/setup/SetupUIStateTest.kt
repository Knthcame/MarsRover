package com.knthcame.marsrover.ui.setup

import com.knthcame.marsrover.data.control.models.CardinalDirection
import org.junit.Test

class SetupUIStateTest {
    @Test
    fun isContinueEnabled_returnsTrue_whenNoValueIsEmpty() {
        val state = SetupUIState(
            plateauHeight = "4",
            plateauWidth = "4",
            initialX = "1",
            initialY = "3",
            initialDirection = CardinalDirection.East,
        )

        assert(state.isContinueEnabled)
    }

    @Test
    fun isContinueEnabled_returnsFalse_whenPlateauHeightIsEmpty() {
        val state = SetupUIState(
            plateauHeight = "",
            plateauWidth = "4",
            initialX = "1",
            initialY = "3",
            initialDirection = CardinalDirection.East,
        )

        assert(!state.isContinueEnabled)
    }

    @Test
    fun isContinueEnabled_returnsFalse_whenPlateauWidthIsEmpty() {
        val state = SetupUIState(
            plateauHeight = "4",
            plateauWidth = "",
            initialX = "1",
            initialY = "3",
            initialDirection = CardinalDirection.East,
        )

        assert(!state.isContinueEnabled)
    }

    @Test
    fun isContinueEnabled_returnsFalse_whenInitialXIsEmpty() {
        val state = SetupUIState(
            plateauHeight = "4",
            plateauWidth = "4",
            initialX = "",
            initialY = "3",
            initialDirection = CardinalDirection.East,
        )

        assert(!state.isContinueEnabled)
    }

    @Test
    fun isContinueEnabled_returnsFalse_whenInitialYIsEmpty() {
        val state = SetupUIState(
            plateauHeight = "4",
            plateauWidth = "4",
            initialX = "1",
            initialY = "",
            initialDirection = CardinalDirection.East,
        )

        assert(!state.isContinueEnabled)
    }
}