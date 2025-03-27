package com.knthcame.marsrover.data.control.model

import kotlinx.serialization.Serializable

@Serializable
data class Instructions(
    val topRightCorner: Coordinates,
    val roverPosition: Coordinates,
    val roverDirection: CardinalDirection,
    val movements: String,
)

