package com.knthcame.marsrover.data.control.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class CardinalDirection(val code: CharSequence) {
    @SerialName("N")
    North("N"),

    @SerialName("E")
    East("E"),

    @SerialName("S")
    South("S"),

    @SerialName("W")
    West("W"),
}