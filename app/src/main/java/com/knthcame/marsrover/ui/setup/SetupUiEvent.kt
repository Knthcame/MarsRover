package com.knthcame.marsrover.ui.setup

import com.knthcame.marsrover.data.control.models.CardinalDirection

sealed interface SetupUiEvent {
    data class PlateauHeightChanged(val value: String) : SetupUiEvent
    data class PlateauWidthChanged(val value: String) : SetupUiEvent
    data class InitialXChanged(val value: String) : SetupUiEvent
    data class InitialYChanged(val value: String) : SetupUiEvent
    data class InitialDirectionChanged(val value: CardinalDirection) : SetupUiEvent
}