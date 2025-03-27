package com.knthcame.marsrover.data.calculation

import com.knthcame.marsrover.data.control.models.CardinalDirection
import com.knthcame.marsrover.data.control.models.Coordinates
import com.knthcame.marsrover.data.control.models.Position
import com.knthcame.marsrover.ui.movements.Movement
import org.junit.Test

class RoverPositionCalculatorImplTest {
    private val calculator = RoverPositionCalculatorImpl()
    private val topRightCorner = Coordinates(5, 8)
    private val roverPosition = Position(Coordinates(1, 2), CardinalDirection.North)

    @Test
    fun calculateNextPosition_increasesY_whenMovingAndFacingNorth() {
        val result =
            calculator.calculateNextPosition(topRightCorner, roverPosition, Movement.MoveForward)

        assert(result.roverPosition.y == roverPosition.roverPosition.y + 1)
    }

    @Test
    fun calculateNextPosition_doesNotIncreaseY_whenAlreadyOnPlateauLimit() {
        val result = calculator.calculateNextPosition(
            topRightCorner = topRightCorner,
            initialPosition = Position(topRightCorner, CardinalDirection.North),
            movement = Movement.MoveForward,
        )

        assert(result.roverPosition.y == topRightCorner.y)
    }

    @Test
    fun calculateNextPosition_increasesX_whenMovingAndFacingEast() {
        val result =
            calculator.calculateNextPosition(
                topRightCorner = topRightCorner,
                initialPosition = Position(roverPosition.roverPosition, CardinalDirection.East),
                movement = Movement.MoveForward,
            )

        assert(result.roverPosition.x == roverPosition.roverPosition.x + 1)
    }

    @Test
    fun calculateNextPosition_doesNotIncreaseX_whenAlreadyOnPlateauLimit() {
        val result = calculator.calculateNextPosition(
            topRightCorner = topRightCorner,
            initialPosition = Position(topRightCorner, CardinalDirection.East),
            movement = Movement.MoveForward,
        )

        assert(result.roverPosition.x == topRightCorner.x)
    }

    @Test
    fun calculateNextPosition_decreasesY_whenMovingAndFacingSouth() {
        val result =
            calculator.calculateNextPosition(
                topRightCorner = topRightCorner,
                initialPosition = Position(roverPosition.roverPosition, CardinalDirection.South),
                movement = Movement.MoveForward,
            )

        assert(result.roverPosition.y == roverPosition.roverPosition.y - 1)
    }

    @Test
    fun calculateNextPosition_doesNotDecreaseY_whenAlreadyOnPlateauLimit() {
        val result = calculator.calculateNextPosition(
            topRightCorner = topRightCorner,
            initialPosition = Position(Coordinates(2, 0), CardinalDirection.South),
            movement = Movement.MoveForward,
        )

        assert(result.roverPosition.y == 0)
    }

    @Test
    fun calculateNextPosition_decreasesX_whenMovingAndFacingWest() {
        val result =
            calculator.calculateNextPosition(
                topRightCorner = topRightCorner,
                initialPosition = Position(roverPosition.roverPosition, CardinalDirection.West),
                movement = Movement.MoveForward,
            )

        assert(result.roverPosition.x == roverPosition.roverPosition.x - 1)
    }

    @Test
    fun calculateNextPosition_doesNotDecreaseX_whenAlreadyOnPlateauLimit() {
        val result = calculator.calculateNextPosition(
            topRightCorner = topRightCorner,
            initialPosition = Position(Coordinates(0, 2), CardinalDirection.West),
            movement = Movement.MoveForward,
        )

        assert(result.roverPosition.x == 0)
    }

    @Test
    fun calculateNextPosition_rotatesToEast_WhenRotatingRightAndFacingNorth() {
        val result = calculator.calculateNextPosition(
            topRightCorner = topRightCorner,
            initialPosition = Position(Coordinates(0, 2), CardinalDirection.North),
            movement = Movement.RotateRight,
        )

        assert(result.roverDirection == CardinalDirection.East)
    }

    @Test
    fun calculateNextPosition_rotatesToSouth_WhenRotatingRightAndFacingEast() {
        val result = calculator.calculateNextPosition(
            topRightCorner = topRightCorner,
            initialPosition = Position(Coordinates(0, 2), CardinalDirection.East),
            movement = Movement.RotateRight,
        )

        assert(result.roverDirection == CardinalDirection.South)
    }

    @Test
    fun calculateNextPosition_rotatesToWest_WhenRotatingRightAndFacingSouth() {
        val result = calculator.calculateNextPosition(
            topRightCorner = topRightCorner,
            initialPosition = Position(Coordinates(0, 2), CardinalDirection.South),
            movement = Movement.RotateRight,
        )

        assert(result.roverDirection == CardinalDirection.West)
    }

    @Test
    fun calculateNextPosition_rotatesToNorth_WhenRotatingRightAndFacingWest() {
        val result = calculator.calculateNextPosition(
            topRightCorner = topRightCorner,
            initialPosition = Position(Coordinates(0, 2), CardinalDirection.West),
            movement = Movement.RotateRight,
        )

        assert(result.roverDirection == CardinalDirection.North)
    }

    @Test
    fun calculateNextPosition_rotatesToWest_WhenRotatingLeftAndFacingNorth() {
        val result = calculator.calculateNextPosition(
            topRightCorner = topRightCorner,
            initialPosition = Position(Coordinates(0, 2), CardinalDirection.North),
            movement = Movement.RotateLeft,
        )

        assert(result.roverDirection == CardinalDirection.West)
    }

    @Test
    fun calculateNextPosition_rotatesToSouth_WhenRotatingLeftAndFacingWest() {
        val result = calculator.calculateNextPosition(
            topRightCorner = topRightCorner,
            initialPosition = Position(Coordinates(0, 2), CardinalDirection.West),
            movement = Movement.RotateLeft,
        )

        assert(result.roverDirection == CardinalDirection.South)
    }

    @Test
    fun calculateNextPosition_rotatesToEast_WhenRotatingLeftAndFacingSouth() {
        val result = calculator.calculateNextPosition(
            topRightCorner = topRightCorner,
            initialPosition = Position(Coordinates(0, 2), CardinalDirection.South),
            movement = Movement.RotateLeft,
        )

        assert(result.roverDirection == CardinalDirection.East)
    }

    @Test
    fun calculateNextPosition_rotatesToNorth_WhenRotatingLeftAndFacingEast() {
        val result = calculator.calculateNextPosition(
            topRightCorner = topRightCorner,
            initialPosition = Position(Coordinates(0, 2), CardinalDirection.East),
            movement = Movement.RotateLeft,
        )

        assert(result.roverDirection == CardinalDirection.North)
    }
}