package com.knthcame.marsrover.ui.setup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import com.knthcame.marsrover.foundation.coroutines.collectWithLifecycle
import com.knthcame.marsrover.ui.setup.SetupContract.Effect.NavigateToMovements

@Composable
fun SetupScreenRoute(onNavigate: (NavKey) -> Unit) {
    val viewModel = hiltViewModel<SetupViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    viewModel.effects.collectWithLifecycle { effect ->
        when (effect) {
            is NavigateToMovements -> onNavigate(effect.movements)
        }
    }

    SetupScreen(
        state = state,
        onEvent = { event -> viewModel.push(event) },
    )
}
