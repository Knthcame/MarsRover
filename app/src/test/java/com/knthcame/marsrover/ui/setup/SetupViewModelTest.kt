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
    fun onEvent_updatesPlateauHeight_whenValueIsEmpty() {
        val newValue = ""

        viewModel.onEvent(SetupUiEvent.PlateauHeightChanged(newValue))

        assert(viewModel.uiState.value == defaultUiState.copy(plateauHeight = newValue))
    }

    @Test
    fun onEvent_updatesPlateauHeight_whenValueIsInteger() {
        val newValue = 12.toString()

        viewModel.onEvent(SetupUiEvent.PlateauHeightChanged(newValue))

        assert(viewModel.uiState.value == defaultUiState.copy(plateauHeight = newValue))
    }

    @Test
    fun onEvent_doesNotUpdatePlateauHeight_whenValueIsNotAnInteger() {
        val newValue = "5f"

        viewModel.onEvent(SetupUiEvent.PlateauHeightChanged(newValue))

        assert(viewModel.uiState.value.plateauHeight != newValue)
    }

    @Test
    fun onEvent_updatesPlateauWidth_whenValueIsEmpty() {
        val newValue = ""

        viewModel.onEvent(SetupUiEvent.PlateauWidthChanged(newValue))

        assert(viewModel.uiState.value == defaultUiState.copy(plateauWidth = newValue))
    }

    @Test
    fun onEvent_updatesPlateauWidth_whenValueIsInteger() {
        val newValue = 12.toString()

        viewModel.onEvent(SetupUiEvent.PlateauWidthChanged(newValue))

        assert(viewModel.uiState.value == defaultUiState.copy(plateauWidth = newValue))
    }

    @Test
    fun onEvent_doesNotUpdatePlateauWidth_whenValueIsNotAnInteger() {
        val newValue = "5f"

        viewModel.onEvent(SetupUiEvent.PlateauWidthChanged(newValue))

        assert(viewModel.uiState.value.plateauWidth != newValue)
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