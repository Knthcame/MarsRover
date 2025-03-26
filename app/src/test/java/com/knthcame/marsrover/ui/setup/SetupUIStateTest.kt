package com.knthcame.marsrover.ui.setup

import com.knthcame.marsrover.data.control.model.CardinalDirection
import org.junit.Test

class SetupUIStateTest {
    @Test
    fun isContinueEnabled_returnsTrue_whenNoValueIsEmpty() {
        val state = SetupUIState(
            plateauSize = "4",
            initialX = "1",
            initialY = "3",
            initialDirection = CardinalDirection.East,
        )

        assert(state.isContinueEnabled)
    }

    @Test
    fun isContinueEnabled_returnsFalse_whenPlateauSizeIsEmpty() {
        val state = SetupUIState(
            plateauSize = "",
            initialX = "1",
            initialY = "3",
            initialDirection = CardinalDirection.East,
        )

        assert(!state.isContinueEnabled)
    }

    @Test
    fun isContinueEnabled_returnsFalse_whenInitialXIsEmpty() {
        val state = SetupUIState(
            plateauSize = "4",
            initialX = "",
            initialY = "3",
            initialDirection = CardinalDirection.East,
        )

        assert(!state.isContinueEnabled)
    }

    @Test
    fun isContinueEnabled_returnsFalse_whenInitialYIsEmpty() {
        val state = SetupUIState(
            plateauSize = "4",
            initialX = "1",
            initialY = "",
            initialDirection = CardinalDirection.East,
        )

        assert(!state.isContinueEnabled)
    }
}