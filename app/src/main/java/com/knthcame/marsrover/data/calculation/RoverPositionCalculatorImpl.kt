package com.knthcame.marsrover.data.calculation

import com.knthcame.marsrover.data.control.models.CardinalDirection
import com.knthcame.marsrover.data.control.models.Coordinates
import com.knthcame.marsrover.data.control.models.Position
import com.knthcame.marsrover.ui.movements.Movement
import kotlin.math.max
import kotlin.math.min

class RoverPositionCalculatorImpl : RoverPositionCalculator {
    override fun calculateNextPosition(
        topRightCorner: Coordinates,
        initialPosition: Position,
        movement: Movement,
    ): Position {
        return when (movement) {
            Movement.MoveForward -> moveForward(topRightCorner, initialPosition)
            Movement.RotateLeft -> rotateLeft(initialPosition)
            Movement.RotateRight -> rotateRight(initialPosition)
        }
    }

    private fun moveForward(topRightCorner: Coordinates, initialPosition: Position): Position {
        val newCoordinates = when (initialPosition.roverDirection) {
            CardinalDirection.North -> moveNorth(topRightCorner.y, initialPosition.roverPosition)
            CardinalDirection.East -> moveEast(topRightCorner.x, initialPosition.roverPosition)
            CardinalDirection.South -> moveSouth(initialPosition.roverPosition)
            CardinalDirection.West -> moveWest(initialPosition.roverPosition)
        }

        return Position(newCoordinates, initialPosition.roverDirection)
    }

    private fun moveNorth(plateauHeight: Int, initialCoordinates: Coordinates): Coordinates {
        val y = min(plateauHeight, initialCoordinates.y + 1)
        return Coordinates(initialCoordinates.x, y)
    }

    private fun moveEast(plateauWidth: Int, initialCoordinates: Coordinates): Coordinates {
        val x = min(plateauWidth, initialCoordinates.x + 1)
        return Coordinates(x, initialCoordinates.y)
    }

    private fun moveSouth(initialCoordinates: Coordinates): Coordinates {
        val y = max(0, initialCoordinates.y - 1)
        return Coordinates(initialCoordinates.x, y)
    }

    private fun moveWest(initialCoordinates: Coordinates): Coordinates {
        val x = max(0, initialCoordinates.x - 1)
        return Coordinates(x, initialCoordinates.y)
    }

    private fun rotateRight(initialPosition: Position): Position {
        val newDirection = when (initialPosition.roverDirection) {
            CardinalDirection.North -> CardinalDirection.East
            CardinalDirection.East -> CardinalDirection.South
            CardinalDirection.South -> CardinalDirection.West
            CardinalDirection.West -> CardinalDirection.North
        }

        return Position(initialPosition.roverPosition, newDirection)
    }

    private fun rotateLeft(initialPosition: Position): Position {
        val newDirection = when (initialPosition.roverDirection) {
            CardinalDirection.North -> CardinalDirection.West
            CardinalDirection.East -> CardinalDirection.North
            CardinalDirection.South -> CardinalDirection.East
            CardinalDirection.West -> CardinalDirection.South
        }

        return Position(initialPosition.roverPosition, newDirection)
    }
}