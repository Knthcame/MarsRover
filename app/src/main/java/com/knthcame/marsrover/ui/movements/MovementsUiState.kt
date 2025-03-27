package com.knthcame.marsrover.ui.movements

import com.knthcame.marsrover.data.control.models.CardinalDirection
import com.knthcame.marsrover.data.control.models.Coordinates
import com.knthcame.marsrover.data.control.models.Instructions

data class MovementsUiState(
    val instructions: Instructions,
    val input: String,
    val output: String,
    val outputReceived: Boolean,
) {
    companion object {
        fun default() = MovementsUiState(
            instructions = Instructions(
                topRightCorner = Coordinates(5, 5),
                roverPosition = Coordinates(0, 0),
                roverDirection = CardinalDirection.North,
                movements = "",
            ),
            input = "",
            output = "",
            outputReceived = false,
        )
    }
}