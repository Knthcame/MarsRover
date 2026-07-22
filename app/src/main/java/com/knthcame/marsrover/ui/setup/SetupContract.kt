package com.knthcame.marsrover.ui.setup

import com.knthcame.marsrover.data.control.models.CardinalDirection
import com.knthcame.marsrover.ui.navigation.Movements

interface SetupContract {
    data class State(
        val plateauHeight: Int?,
        val plateauWidth: Int?,
        val initialX: Int?,
        val initialY: Int?,
        val initialDirection: CardinalDirection,
    ) {
        companion object {
            fun default(): State = State(
                plateauHeight = 5,
                plateauWidth = 5,
                initialX = 0,
                initialY = 0,
                initialDirection = CardinalDirection.North,
            )
        }

        val isContinueEnabled: Boolean
            get() = plateauHeight != null &&
                plateauWidth != null &&
                initialX != null &&
                initialY != null
    }

    sealed interface UiEvent {
        data class PlateauHeightChanged(val value: String) : UiEvent
        data class PlateauWidthChanged(val value: String) : UiEvent
        data class InitialXChanged(val value: String) : UiEvent
        data class InitialYChanged(val value: String) : UiEvent
        data class InitialDirectionChanged(val value: CardinalDirection) : UiEvent
        data object OnContinueClicked : UiEvent
    }

    sealed interface Effect {
        data class NavigateToMovements(val movements: Movements) : Effect
    }
}
