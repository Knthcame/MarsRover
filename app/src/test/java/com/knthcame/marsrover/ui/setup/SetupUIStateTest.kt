package com.knthcame.marsrover.ui.setup

import com.knthcame.marsrover.data.control.models.CardinalDirection
import org.junit.Test

class SetupUIStateTest {
    @Test
    fun isContinueEnabled_returnsTrue_whenNoValueIsEmpty() {
        val state = SetupContract.State(
            plateauHeight = 4,
            plateauWidth = 4,
            initialX = 1,
            initialY = 3,
            initialDirection = CardinalDirection.East,
        )

        assert(state.isContinueEnabled)
    }

    @Test
    fun isContinueEnabled_returnsFalse_whenPlateauHeightIsEmpty() {
        val state = SetupContract.State(
            plateauHeight = null,
            plateauWidth = 4,
            initialX = 1,
            initialY = 3,
            initialDirection = CardinalDirection.East,
        )

        assert(!state.isContinueEnabled)
    }

    @Test
    fun isContinueEnabled_returnsFalse_whenPlateauWidthIsEmpty() {
        val state = SetupContract.State(
            plateauHeight = 4,
            plateauWidth = null,
            initialX = 1,
            initialY = 3,
            initialDirection = CardinalDirection.East,
        )

        assert(!state.isContinueEnabled)
    }

    @Test
    fun isContinueEnabled_returnsFalse_whenInitialXIsEmpty() {
        val state = SetupContract.State(
            plateauHeight = 4,
            plateauWidth = 4,
            initialX = null,
            initialY = 3,
            initialDirection = CardinalDirection.East,
        )

        assert(!state.isContinueEnabled)
    }

    @Test
    fun isContinueEnabled_returnsFalse_whenInitialYIsEmpty() {
        val state = SetupContract.State(
            plateauHeight = 4,
            plateauWidth = 4,
            initialX = 1,
            initialY = null,
            initialDirection = CardinalDirection.East,
        )

        assert(!state.isContinueEnabled)
    }
}
