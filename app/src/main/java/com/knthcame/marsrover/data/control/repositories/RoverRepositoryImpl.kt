package com.knthcame.marsrover.data.control.repositories

import com.knthcame.marsrover.data.control.models.Instructions
import com.knthcame.marsrover.data.control.models.Position
import com.knthcame.marsrover.data.control.sources.RoverDao
import kotlinx.serialization.json.Json

class RoverRepositoryImpl(private val roverDao: RoverDao) : RoverRepository {
    override suspend fun send(instructions: Instructions): Position {
        val json = Json.encodeToString(instructions)
        val result = roverDao.send(json)
        return Json.decodeFromString<Position>(result)
    }
}