package com.knthcame.marsrover.ui.movements

import androidx.lifecycle.viewModelScope
import com.knthcame.marsrover.data.calculation.RoverPositionCalculator
import com.knthcame.marsrover.data.control.models.Coordinates
import com.knthcame.marsrover.data.control.models.Instructions
import com.knthcame.marsrover.data.control.models.Position
import com.knthcame.marsrover.data.control.repositories.RoverRepository
import com.knthcame.marsrover.foundation.coroutines.CoroutineScopeProvider
import com.knthcame.marsrover.foundation.viewmodels.MviViewModel
import com.knthcame.marsrover.foundation.viewmodels.ViewModelAssistedFactory
import com.knthcame.marsrover.ui.movements.MovementsContract.Effect
import com.knthcame.marsrover.ui.movements.MovementsContract.State
import com.knthcame.marsrover.ui.movements.MovementsContract.UiEvent
import com.knthcame.marsrover.ui.navigation.Movements
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

@HiltViewModel(assistedFactory = MovementsViewModel.Factory::class)
class MovementsViewModel @AssistedInject constructor(
    @Assisted route: Movements,
    private val roverRepository: RoverRepository,
    private val roverPositionCalculator: RoverPositionCalculator,
    coroutineScopeProvider: CoroutineScopeProvider,
) : MviViewModel<State, UiEvent, Effect>(
    viewModelScope = coroutineScopeProvider.viewModel,
    eventsCoroutineContext = coroutineScopeProvider.events,
    uiCoroutineContext = coroutineScopeProvider.ui,
) {

    @AssistedFactory
    interface Factory : ViewModelAssistedFactory<MovementsViewModel, Movements>

    private val json = Json { prettyPrint = true }
    private val _state = MutableStateFlow(
        State(
            instructions = Instructions(
                topRightCorner = Coordinates(route.plateauWidth, route.plateauHeight),
                roverPosition = Coordinates(route.initialPositionX, route.initialPositionY),
                roverDirection = route.initialDirection,
                movements = "",
            ),
            input = "",
            output = "",
            outputReceived = false,
            predictedPositions = listOf(
                Position(
                    roverPosition = Coordinates(route.initialPositionX, route.initialPositionY),
                    roverDirection = route.initialDirection,
                ),
            ),
        ),
    )

    override val state: StateFlow<State> = _state

    override fun onUiEvent(uiEvent: UiEvent) = when (uiEvent) {
        is UiEvent.AddMovement -> addMovement(uiEvent.movement)
        UiEvent.DismissOutputDialog -> dismissOutputDialog()
        UiEvent.RemoveLastMovement -> removeLastMovement()
        UiEvent.SendMovements -> sendMovements()
        UiEvent.NavigateBackClick -> emitEffect(Effect.NavigateBack)
    }

    private fun addMovement(movement: Movement) = _state.update { state ->
        val nextPosition = roverPositionCalculator.calculateNextPosition(
            topRightCorner = state.instructions.topRightCorner,
            currentPosition = state.predictedPositions.last(),
            movement = movement,
        )
        val movements = state.instructions.movements
        state.copy(
            instructions = state.instructions.copy(movements = movements + movement.code),
            predictedPositions = state.predictedPositions + nextPosition,
        )
    }

    private fun removeLastMovement() = _state.update { state ->
        val oldMovements = state.instructions.movements
        val newMovements = oldMovements.substring(0, oldMovements.lastIndex)
        state.copy(
            instructions = state.instructions.copy(movements = newMovements),
            predictedPositions = state.predictedPositions.subList(
                0,
                state.predictedPositions.lastIndex,
            ),
        )
    }

    private fun sendMovements() {
        viewModelScope.launch {
            val state = state.value
            val output = roverRepository.send(state.instructions)

            _state.update { state ->
                state.copy(
                    input = json.encodeToString(state.instructions),
                    output = buildString {
                        append(output.roverPosition.x)
                        append(" ")
                        append(output.roverPosition.y)
                        append(" ")
                        append(output.roverDirection.code)
                    },
                    outputReceived = true,
                )
            }
        }
    }

    private fun dismissOutputDialog() = _state.update { state ->
        state.copy(input = "", output = "", outputReceived = false)
    }
}
