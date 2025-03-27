package com.knthcame.marsrover.data.control.model

import kotlinx.serialization.Serializable

@Serializable
data class Output(
    val roverPosition: Coordinates,
    val roverDirection: CardinalDirection,
)
