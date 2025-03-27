package com.knthcame.marsrover.data.calculation

import com.knthcame.marsrover.data.control.models.Coordinates
import com.knthcame.marsrover.data.control.models.Position
import com.knthcame.marsrover.ui.movements.Movement

fun interface RoverPositionCalculator {
    fun calculateNextPosition(
        topRightCorner: Coordinates,
        currentPosition: Position,
        movement: Movement,
    ): Position
}