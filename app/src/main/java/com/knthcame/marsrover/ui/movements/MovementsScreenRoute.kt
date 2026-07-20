package com.knthcame.marsrover.ui.movements

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.knthcame.marsrover.foundation.coroutines.collectWithLifecycle
import com.knthcame.marsrover.ui.movements.MovementsContract.Effect
import com.knthcame.marsrover.ui.navigation.Movements

@Composable
fun MovementsScreenRoute(movements: Movements, onNavigateBack: () -> Unit) {
    val viewModel = hiltViewModel<MovementsViewModel, MovementsViewModel.Factory> { factory ->
        factory.create(movements)
    }
    val state by viewModel.state.collectAsStateWithLifecycle()

    MovementsScreen(state = state, onPushEvent = viewModel::push)

    viewModel.effects.collectWithLifecycle { effect ->
        when (effect) {
            Effect.NavigateBack -> onNavigateBack()
        }
    }
}
