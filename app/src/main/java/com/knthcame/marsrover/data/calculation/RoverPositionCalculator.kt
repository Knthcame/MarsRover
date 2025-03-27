package com.knthcame.marsrover.data.calculation

import com.knthcame.marsrover.data.control.models.Position
import com.knthcame.marsrover.ui.movements.Movement

fun interface RoverPositionCalculator {
    fun calculateNextPosition(
        plateauSize: Int,
        initialPosition: Position,
        movement: Movement,
    ): Position
}