package com.knthcame.marsrover.data.control.models

import kotlinx.serialization.Serializable

@Serializable
data class Position(
    val roverPosition: Coordinates,
    val roverDirection: CardinalDirection,
)
