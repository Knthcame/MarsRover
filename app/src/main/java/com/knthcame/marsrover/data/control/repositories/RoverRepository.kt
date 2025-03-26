package com.knthcame.marsrover.data.control.repositories

import com.knthcame.marsrover.data.control.model.Instructions
import com.knthcame.marsrover.data.control.model.Output

fun interface RoverRepository {
    suspend fun send(instructions: Instructions): Output
}