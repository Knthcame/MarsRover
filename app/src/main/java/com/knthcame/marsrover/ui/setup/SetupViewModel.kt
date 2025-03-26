package com.knthcame.marsrover.ui.setup

import androidx.lifecycle.ViewModel
import com.knthcame.marsrover.data.control.model.CardinalDirection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class SetupUIState(
    val plateauSize: String,
    val initialX: String,
    val initialY: String,
    val initialDirection: CardinalDirection,
) {
    companion object {
        fun default(): SetupUIState =
            SetupUIState(
                plateauSize = "5",
                initialX = "0",
                initialY = "0",
                initialDirection = CardinalDirection.North,
            )
    }

    val isContinueEnabled: Boolean
        get() = plateauSize.isNotEmpty() && initialX.isNotEmpty() && initialY.isNotEmpty()
}

sealed interface SetupUiEvent {
    data class PlateauSizeChanged(val value: String) : SetupUiEvent
    data class InitialXChanged(val value: String) : SetupUiEvent
    data class InitialYChanged(val value: String) : SetupUiEvent
    data class InitialDirectionChanged(val value: CardinalDirection) : SetupUiEvent
}

class SetupViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SetupUIState.default())

    val uiState: StateFlow<SetupUIState> = _uiState

    fun onEvent(event: SetupUiEvent) {
        when (event) {
            is SetupUiEvent.InitialDirectionChanged -> onInitialDirectionChanged(event.value)
            is SetupUiEvent.InitialXChanged -> onInitialXChanged(event.value)
            is SetupUiEvent.InitialYChanged -> onInitialYChanged(event.value)
            is SetupUiEvent.PlateauSizeChanged -> onPlateauSizeChanged(event.value)
        }
    }

    private fun onPlateauSizeChanged(newValue: String) {
        if (newValue.isEmpty()) {
            _uiState.update { oldValue ->
                oldValue.copy(plateauSize = "")
            }
            return
        }

        newValue.toIntOrNull()?.let {
            _uiState.update { oldValue ->
                oldValue.copy(plateauSize = newValue)
            }
        }
    }

    private fun onInitialXChanged(newValue: String) {
        if (newValue.isEmpty()) {
            _uiState.update { oldValue ->
                oldValue.copy(initialX = "")
            }
            return
        }

        newValue.toIntOrNull()?.let {
            _uiState.update { oldValue ->
                oldValue.copy(initialX = newValue)
            }
        }
    }

    private fun onInitialYChanged(newValue: String) {
        if (newValue.isEmpty()) {
            _uiState.update { oldValue ->
                oldValue.copy(initialY = "")
            }
            return
        }

        newValue.toIntOrNull()?.let {
            _uiState.update { oldValue ->
                oldValue.copy(initialY = newValue)
            }
        }
    }

    private fun onInitialDirectionChanged(newValue: CardinalDirection) {
        _uiState.update { oldValue ->
            oldValue.copy(initialDirection = newValue)
        }
    }
}