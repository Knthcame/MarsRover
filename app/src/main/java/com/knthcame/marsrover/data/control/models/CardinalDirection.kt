package com.knthcame.marsrover.data.control.models

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Keep
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