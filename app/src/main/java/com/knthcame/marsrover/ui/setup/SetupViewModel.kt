package com.knthcame.marsrover.ui.setup

import androidx.core.text.isDigitsOnly
import com.knthcame.marsrover.data.control.models.CardinalDirection
import com.knthcame.marsrover.foundation.coroutines.CoroutineScopeProvider
import com.knthcame.marsrover.foundation.viewmodels.MviViewModel
import com.knthcame.marsrover.ui.navigation.Movements
import com.knthcame.marsrover.ui.setup.SetupContract.Effect
import com.knthcame.marsrover.ui.setup.SetupContract.State
import com.knthcame.marsrover.ui.setup.SetupContract.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class SetupViewModel @Inject constructor(coroutineScopeProvider: CoroutineScopeProvider) :
    MviViewModel<State, UiEvent, Effect>(
        viewModelScope = coroutineScopeProvider.viewModel,
        eventsCoroutineContext = coroutineScopeProvider.events,
    ) {
    private val _state = MutableStateFlow(State.default())

    override val state: StateFlow<State> = _state

    override fun onUiEvent(uiEvent: UiEvent, state: State) {
        when (uiEvent) {
            is UiEvent.InitialDirectionChanged -> onInitialDirectionChanged(uiEvent.value)
            is UiEvent.InitialXChanged -> onInitialXChanged(uiEvent.value)
            is UiEvent.InitialYChanged -> onInitialYChanged(uiEvent.value)
            is UiEvent.PlateauHeightChanged -> onPlateauHeightChanged(uiEvent.value)
            is UiEvent.PlateauWidthChanged -> onPlateauWidthChanged(uiEvent.value)
            UiEvent.OnContinueClicked -> onContinueClicked(state)
        }
    }

    private fun onPlateauWidthChanged(newValue: String) {
        if (!newValue.isValidInteger()) return

        _state.update { state ->
            state.copy(plateauWidth = newValue.toIntOrNull())
        }
    }

    private fun onPlateauHeightChanged(newValue: String) {
        if (!newValue.isValidInteger()) return

        _state.update { state ->
            state.copy(plateauHeight = newValue.toIntOrNull())
        }
    }

    private fun onInitialXChanged(newValue: String) {
        if (!newValue.isValidInteger()) return

        _state.update { state ->
            state.copy(initialX = newValue.toIntOrNull())
        }
    }

    private fun onInitialYChanged(newValue: String) {
        if (!newValue.isValidInteger()) return

        _state.update { state ->
            state.copy(initialY = newValue.toIntOrNull())
        }
    }

    private fun onInitialDirectionChanged(newValue: CardinalDirection) = _state.update { state ->
        state.copy(initialDirection = newValue)
    }

    private fun onContinueClicked(state: State) = try {
        emitEffect(
            Effect.NavigateToMovements(
                Movements(
                    plateauHeight = state.plateauHeight!!,
                    plateauWidth = state.plateauWidth!!,
                    initialPositionX = state.initialX!!,
                    initialPositionY = state.initialY!!,
                    initialDirection = state.initialDirection,
                ),
            ),
        )
    } catch (e: NullPointerException) {
        e.printStackTrace()
    }

    private fun String.isValidInteger() = isEmpty() || isDigitsOnly()
}
