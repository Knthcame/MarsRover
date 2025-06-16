package com.knthcame.marsrover.ui.setup

import androidx.lifecycle.ViewModel
import com.knthcame.marsrover.data.control.models.CardinalDirection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SetupViewModel @Inject constructor(viewModelScope: CoroutineScope) :
    ViewModel(viewModelScope) {
    private val _uiState = MutableStateFlow(SetupUIState.default())

    val uiState: StateFlow<SetupUIState> = _uiState

    fun onEvent(event: SetupUiEvent) {
        when (event) {
            is SetupUiEvent.InitialDirectionChanged -> onInitialDirectionChanged(event.value)
            is SetupUiEvent.InitialXChanged -> onInitialXChanged(event.value)
            is SetupUiEvent.InitialYChanged -> onInitialYChanged(event.value)
            is SetupUiEvent.PlateauHeightChanged -> onPlateauHeightChanged(event.value)
            is SetupUiEvent.PlateauWidthChanged -> onPlateauWidthChanged(event.value)
        }
    }

    private fun onPlateauWidthChanged(newValue: String) {
        if (newValue.isEmpty()) {
            _uiState.update { oldValue ->
                oldValue.copy(plateauWidth = newValue)
            }
            return
        }

        newValue.toIntOrNull()?.let {
            _uiState.update { oldValue ->
                oldValue.copy(plateauWidth = newValue)
            }
        }
    }

    private fun onPlateauHeightChanged(newValue: String) {
        if (newValue.isEmpty()) {
            _uiState.update { oldValue ->
                oldValue.copy(plateauHeight = newValue)
            }
            return
        }

        newValue.toIntOrNull()?.let {
            _uiState.update { oldValue ->
                oldValue.copy(plateauHeight = newValue)
            }
        }
    }

    private fun onInitialXChanged(newValue: String) {
        if (newValue.isEmpty()) {
            _uiState.update { oldValue ->
                oldValue.copy(initialX = newValue)
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
                oldValue.copy(initialY = newValue)
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