package com.knthcame.marsrover.ui.setup

import com.knthcame.marsrover.data.control.models.CardinalDirection

data class SetupUIState(
    val plateauHeight: String,
    val plateauWidth: String,
    val initialX: String,
    val initialY: String,
    val initialDirection: CardinalDirection,
) {
    companion object {
        fun default(): SetupUIState =
            SetupUIState(
                plateauHeight = "5",
                plateauWidth = "5",
                initialX = "0",
                initialY = "0",
                initialDirection = CardinalDirection.North,
            )
    }

    val isContinueEnabled: Boolean
        get() = plateauHeight.isNotEmpty() && plateauWidth.isNotEmpty() && initialX.isNotEmpty() && initialY.isNotEmpty()
}