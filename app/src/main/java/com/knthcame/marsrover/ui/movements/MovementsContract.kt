package com.knthcame.marsrover.ui.movements

import com.knthcame.marsrover.data.control.models.Instructions
import com.knthcame.marsrover.data.control.models.Position

object MovementsContract {
    data class State(
        val instructions: Instructions,
        val input: String,
        val output: String,
        val outputReceived: Boolean,
        val predictedPositions: List<Position>,
    )

    sealed interface UiEvent {
        data object NavigateBackClick : UiEvent
        data class AddMovement(val movement: Movement) : UiEvent
        data object RemoveLastMovement : UiEvent
        data object SendMovements : UiEvent
        data object DismissOutputDialog : UiEvent
    }

    sealed interface Effect {
        data object NavigateBack : Effect
    }
}
