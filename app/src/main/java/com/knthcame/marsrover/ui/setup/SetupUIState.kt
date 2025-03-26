package com.knthcame.marsrover.ui.setup

import com.knthcame.marsrover.data.control.model.CardinalDirection

data class SetupUIState(
    val plateauSize: String,
    val initialX: String,
    val initialY: String,
    val initialDirection: CardinalDirection,
) {
    companion object {
        fun default(): SetupUIState =
            SetupUIState(
                plateauSize = "5",
                initialX = "0",
                initialY = "0",
                initialDirection = CardinalDirection.North,
            )
    }

    val isContinueEnabled: Boolean
        get() = plateauSize.isNotEmpty() && initialX.isNotEmpty() && initialY.isNotEmpty()
}