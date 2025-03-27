package com.knthcame.marsrover.ui.movements

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.knthcame.marsrover.data.calculation.RoverPositionCalculator
import com.knthcame.marsrover.data.control.models.Coordinates
import com.knthcame.marsrover.data.control.models.Instructions
import com.knthcame.marsrover.data.control.models.Position
import com.knthcame.marsrover.data.control.repositories.RoverRepository
import com.knthcame.marsrover.ui.Movements
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class MovementsViewModel(
    savedStateHandle: SavedStateHandle,
    private val roverRepository: RoverRepository,
    private val roverPositionCalculator: RoverPositionCalculator,
    private val viewModeScope: CoroutineScope,
) : ViewModel(viewModeScope) {
    private val route = savedStateHandle.toRoute<Movements>()
    private val json = Json { prettyPrint = true }
    private val _uiState = MutableStateFlow(
        MovementsUiState(
            instructions = Instructions(
                topRightCorner = Coordinates(route.plateauWidth, route.plateauHeight),
                roverPosition = Coordinates(route.initialPositionX, route.initialPositionY),
                roverDirection = route.initialDirection,
                movements = "",
            ),
            input = "",
            output = "",
            outputReceived = false,
        )
    )
    private val _roverPositions = MutableStateFlow(
        listOf(
            Position(
                roverPosition = Coordinates(route.initialPositionX, route.initialPositionY),
                roverDirection = route.initialDirection,
            )
        )
    )

    val uiState: StateFlow<MovementsUiState> = _uiState
    val roverPositions: StateFlow<List<Position>> = _roverPositions

    fun addMovement(movement: Movement) {
        _uiState.update { oldValue ->
            val movements = oldValue.instructions.movements
            oldValue.copy(
                instructions = oldValue.instructions.copy(movements = movements + movement.code),
            )
        }
        val nextPosition = roverPositionCalculator.calculateNextPosition(
            topRightCorner = uiState.value.instructions.topRightCorner,
            currentPosition = roverPositions.value.last(),
            movement = movement,
        )
        _roverPositions.update { oldValue ->
            oldValue + nextPosition
        }
    }

    fun removeLastMovement() {
        _uiState.update { oldValue ->
            val oldMovements = oldValue.instructions.movements
            val newMovements = oldMovements.substring(0, oldMovements.lastIndex)
            oldValue.copy(
                instructions = oldValue.instructions.copy(movements = newMovements),
            )
        }
        _roverPositions.update { oldValue ->
            oldValue.subList(0, oldValue.lastIndex)
        }
    }

    fun sendMovements() {
        viewModeScope.launch {
            val state = uiState.value
            val output = roverRepository.send(state.instructions)

            _uiState.update { oldValue ->
                oldValue.copy(
                    input = json.encodeToString(state.instructions),
                    output = "${output.roverPosition.x} ${output.roverPosition.y} ${output.roverDirection.code}",
                    outputReceived = true,
                )
            }
        }
    }

    fun dismissOutputDialog() {
        _uiState.update { oldValue ->
            oldValue.copy(input = "", output = "", outputReceived = false)
        }
    }
}