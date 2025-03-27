package com.knthcame.marsrover.ui.movements

import com.knthcame.marsrover.data.control.models.CardinalDirection
import com.knthcame.marsrover.data.control.models.Coordinates

data class MovementsUiState(
    val plateauSize: Int,
    val initialPosition: Coordinates,
    val initialDirection: CardinalDirection,
    val movements: List<Movement>,
    val input: String,
    val output: String,
    val outputReceived: Boolean,
) {
    companion object {
        fun default() = MovementsUiState(
            plateauSize = 5,
            initialPosition = Coordinates(0, 0),
            initialDirection = CardinalDirection.North,
            movements = emptyList(),
            input = "",
            output = "",
            outputReceived = false,
        )
    }
}