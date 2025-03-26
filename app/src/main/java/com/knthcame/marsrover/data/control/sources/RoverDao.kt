package com.knthcame.marsrover.data.control.sources

fun interface RoverDao {
    suspend fun send(instructions: String): String
}