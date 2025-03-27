package com.knthcame.marsrover.ui.setup

import com.knthcame.marsrover.data.control.models.CardinalDirection
import com.knthcame.marsrover.testViewModelScope
import org.junit.Test

class SetupViewModelTest {
    private val defaultUiState get() = SetupUIState.default()
    private val viewModel = SetupViewModel(testViewModelScope)

    @Test
    fun uiState_returnsDefaultState_onInit() {
        val actual = viewModel.uiState.value

        assert(actual == defaultUiState)
    }

    @Test
    fun onEvent_updatesPlateauSize_whenValueIsEmpty() {
        val newValue = ""

        viewModel.onEvent(SetupUiEvent.PlateauSizeChanged(newValue))

        assert(viewModel.uiState.value == defaultUiState.copy(plateauSize = newValue))
    }

    @Test
    fun onEvent_updatesPlateauSize_whenValueIsInteger() {
        val newValue = 12.toString()

        viewModel.onEvent(SetupUiEvent.PlateauSizeChanged(newValue))

        assert(viewModel.uiState.value == defaultUiState.copy(plateauSize = newValue))
    }

    @Test
    fun onEvent_doesNotUpdatePlateauSize_whenValueIsNotAnInteger() {
        val newValue = "5f"

        viewModel.onEvent(SetupUiEvent.PlateauSizeChanged(newValue))

        assert(viewModel.uiState.value.plateauSize != newValue)
    }

    @Test
    fun onEvent_updatesInitialX_whenValueIsEmpty() {
        val newValue = ""

        viewModel.onEvent(SetupUiEvent.InitialXChanged(newValue))

        assert(viewModel.uiState.value == defaultUiState.copy(initialX = newValue))
    }

    @Test
    fun onEvent_updatesInitialX_whenValueIsInteger() {
        val newValue = 4.toString()

        viewModel.onEvent(SetupUiEvent.InitialXChanged(newValue))

        assert(viewModel.uiState.value == defaultUiState.copy(initialX = newValue))
    }

    @Test
    fun onEvent_doesNotUpdateInitialX_whenValueIsNotAnInteger() {
        val newValue = "2a"

        viewModel.onEvent(SetupUiEvent.InitialXChanged(newValue))

        assert(viewModel.uiState.value.initialX != newValue)
    }

    @Test
    fun onEvent_updatesInitialY_whenValueIsEmpty() {
        val newValue = ""

        viewModel.onEvent(SetupUiEvent.InitialYChanged(newValue))

        assert(viewModel.uiState.value == defaultUiState.copy(initialY = newValue))
    }

    @Test
    fun onEvent_updatesInitialY_whenValueIsInteger() {
        val newValue = 1.toString()

        viewModel.onEvent(SetupUiEvent.InitialYChanged(newValue))

        assert(viewModel.uiState.value == defaultUiState.copy(initialY = newValue))
    }

    @Test
    fun onEvent_doesNotUpdateInitialY_whenValueIsNotAnInteger() {
        val newValue = "3k"

        viewModel.onEvent(SetupUiEvent.InitialYChanged(newValue))

        assert(viewModel.uiState.value.initialY != newValue)
    }

    @Test
    fun onEvent_updatesInitialDirection_onInitialDirectionChangedEvent() {
        val newValue = CardinalDirection.South

        viewModel.onEvent(SetupUiEvent.InitialDirectionChanged(newValue))

        assert(viewModel.uiState.value == defaultUiState.copy(initialDirection = newValue))
    }
}