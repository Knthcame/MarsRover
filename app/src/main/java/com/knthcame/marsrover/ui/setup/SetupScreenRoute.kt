package com.knthcame.marsrover.ui.setup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.knthcame.marsrover.data.control.models.CardinalDirection
import com.knthcame.marsrover.data.control.models.Coordinates

@Composable
fun SetupScreenRoute(
    onSetupCompleted: (
        topRightCorner: Coordinates,
        initialPosition: Coordinates,
        initialDirection: CardinalDirection,
    ) -> Unit,
) {
    val viewModel = hiltViewModel<SetupViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    SetupScreen(
        uiState = uiState,
        onEvent = { event -> viewModel.onEvent(event) },
        onSetupCompleted = {
            onSetupCompleted(
                Coordinates(
                    x = uiState.plateauWidth.toInt(),
                    y = uiState.plateauHeight.toInt(),
                ),
                Coordinates(
                    x = uiState.initialX.toInt(),
                    y = uiState.initialY.toInt(),
                ),
                uiState.initialDirection,
            )
        },
    )
}
