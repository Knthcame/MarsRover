package com.knthcame.marsrover.data.control.repositories

import com.knthcame.marsrover.data.control.model.Instructions
import com.knthcame.marsrover.data.control.model.Output
import com.knthcame.marsrover.data.control.sources.RoverDao
import kotlinx.serialization.json.Json

class RoverRepositoryImpl(private val roverDao: RoverDao) : RoverRepository {
    override suspend fun send(instructions: Instructions): Output {
        val json = Json.encodeToString(instructions)
        val result = roverDao.send(json)
        return Json.decodeFromString<Output>(result)
    }
}