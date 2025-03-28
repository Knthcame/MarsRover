package com.knthcame.marsrover.data.control.repositories

import com.knthcame.marsrover.data.control.models.CardinalDirection
import com.knthcame.marsrover.data.control.models.Coordinates
import com.knthcame.marsrover.data.control.models.Instructions
import com.knthcame.marsrover.data.control.models.Position
import com.knthcame.marsrover.data.control.sources.RoverDao
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test

class RoverRepositoryImplTest {
    private lateinit var roverDao: RoverDao
    private lateinit var repositoryImpl: RoverRepositoryImpl
    private val roverPosition = Position(Coordinates(1, 2), CardinalDirection.West)

    @Before
    fun setUp() {
        roverDao = mockk<RoverDao> {
            coEvery { send(any()) } returns Json.encodeToString(roverPosition)
        }
        repositoryImpl = RoverRepositoryImpl(roverDao)
    }

    @Test
    fun send_sendsInstructionToDao_Serialized() = runTest {
        val instructions = Instructions(
            topRightCorner = Coordinates(7, 7),
            roverPosition = Coordinates(3, 2),
            roverDirection = CardinalDirection.South,
            movements = "MRMLMMMR",
        )

        repositoryImpl.send(instructions)

        coVerify { roverDao.send(Json.encodeToString(instructions)) }
    }

    @Test
    fun send_returnsOutputDeserialized_onCall() = runTest {
        val instructions = Instructions(
            topRightCorner = Coordinates(5, 5),
            roverPosition = Coordinates(1, 2),
            roverDirection = CardinalDirection.South,
            movements = "LLMMMRMMM",
        )

        val result = repositoryImpl.send(instructions)

        assert(result == roverPosition)
    }
}