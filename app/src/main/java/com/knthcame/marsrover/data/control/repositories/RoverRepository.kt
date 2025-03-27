package com.knthcame.marsrover.data.control.repositories

import com.knthcame.marsrover.data.control.models.Instructions
import com.knthcame.marsrover.data.control.models.Position

fun interface RoverRepository {
    suspend fun send(instructions: Instructions): Position
}