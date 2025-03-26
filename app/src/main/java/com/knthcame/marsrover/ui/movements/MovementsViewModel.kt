package com.knthcame.marsrover.ui.movements

import androidx.lifecycle.ViewModel
import com.knthcame.marsrover.data.control.model.Coordinates
import com.knthcame.marsrover.data.control.model.Instructions
import com.knthcame.marsrover.data.control.model.Movement
import com.knthcame.marsrover.data.control.repositories.RoverRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class MovementsViewModel(
    private val roverRepository: RoverRepository,
    private val viewModeScope: CoroutineScope,
) : ViewModel(viewModeScope) {
    private val _uiState = MutableStateFlow(MovementsUiState.default())
    private val json = Json { prettyPrint = true }

    val uiState: StateFlow<MovementsUiState> = _uiState

    fun addMovement(movement: Movement) {
        _uiState.update { oldValue ->
            oldValue.copy(
                movements = oldValue.movements + movement,
            )
        }
    }

    fun removeLastMovement() {
        _uiState.update { oldValue ->
            oldValue.copy(
                movements = oldValue.movements.subList(0, oldValue.movements.lastIndex),
            )
        }
    }

    fun sendMovements() {
        viewModeScope.launch {
            val state = uiState.value
            val instructions = Instructions(
                topRightCorner = Coordinates(state.plateauSize, state.plateauSize),
                roverPosition = state.initialPosition,
                roverDirection = state.initialDirection,
                movements = state.movements.joinToString(separator = "") { value -> value.code }
            )
            val output = roverRepository.send(instructions)

            _uiState.update { oldValue ->
                oldValue.copy(
                    input = json.encodeToString(instructions),
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