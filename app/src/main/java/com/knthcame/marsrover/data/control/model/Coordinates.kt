package com.knthcame.marsrover.data.control.model

import kotlinx.serialization.Serializable

@Serializable
data class Coordinates(
    val x: Int,
    val y: Int,
)