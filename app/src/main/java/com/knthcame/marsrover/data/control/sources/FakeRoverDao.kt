package com.knthcame.marsrover.data.control.sources

class FakeRoverDao : RoverDao {
    override suspend fun send(instructions: String): String {
        return "{\n" +
                "   \"roverPosition\":{\n" +
                "      \"x\":1,\n" +
                "      \"y\":3\n" +
                "   },\n" +
                "   \"roverDirection\":\"N\"\n" +
                "}"
    }
}