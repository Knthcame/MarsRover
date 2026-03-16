package com.knthcame.marsrover.ui.movements

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.knthcame.marsrover.ui.navigation.Movements

@Composable
fun MovementsScreenRoute(movements: Movements, onNavigateBack: () -> Unit) {
    val viewModel = hiltViewModel<MovementsViewModel, MovementsViewModel.Factory> { factory ->
        factory.create(movements)
    }
    val uiState by viewModel.uiState.collectAsState()
    val roverPositions by viewModel.roverPositions.collectAsState()

    MovementsScreen(
        uiState = uiState,
        roverPositions = roverPositions,
        onNavigateBack = onNavigateBack,
        onAddMovement = { movement -> viewModel.addMovement(movement) },
        onRemoveMovement = { viewModel.removeLastMovement() },
        onConfirm = { viewModel.sendMovements() },
        onDismissOutputDialog = { viewModel.dismissOutputDialog() },
    )
}
