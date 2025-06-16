package com.knthcame.marsrover.data.control.sources

import com.knthcame.marsrover.data.calculation.RoverPositionCalculator
import com.knthcame.marsrover.data.control.models.Instructions
import com.knthcame.marsrover.data.control.models.Position
import com.knthcame.marsrover.ui.movements.Movement
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeRoverDao @Inject constructor(private val roverPositionCalculator: RoverPositionCalculator) :
    RoverDao {
    override suspend fun send(instructions: String): String {
        val data = Json.decodeFromString<Instructions>(instructions)
        val movements = data.movements.map { value ->
            Movement.entries.single { movement -> movement.code == value.toString() }
        }
        var position = Position(data.roverPosition, data.roverDirection)
        movements.forEach { movement ->
            position = roverPositionCalculator.calculateNextPosition(
                topRightCorner = data.topRightCorner,
                currentPosition = position,
                movement = movement,
            )
        }
        return Json.encodeToString(position)
    }
}