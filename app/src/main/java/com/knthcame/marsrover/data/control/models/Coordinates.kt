package com.knthcame.marsrover.data.control.models

import kotlinx.serialization.Serializable

@Serializable
data class Coordinates(
    val x: Int,
    val y: Int,
)