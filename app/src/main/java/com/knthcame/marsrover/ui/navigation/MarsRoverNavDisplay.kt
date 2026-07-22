package com.knthcame.marsrover.ui.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.knthcame.marsrover.data.control.models.CardinalDirection
import com.knthcame.marsrover.ui.home.HomeScreenRoute
import com.knthcame.marsrover.ui.movements.MovementsScreenRoute
import com.knthcame.marsrover.ui.setup.SetupScreenRoute
import kotlinx.serialization.Serializable

@Serializable
data object Home : NavKey

@Serializable
data object Setup : NavKey

@Serializable
data class Movements(
    val plateauHeight: Int,
    val plateauWidth: Int,
    val initialPositionX: Int,
    val initialPositionY: Int,
    val initialDirection: CardinalDirection,
) : NavKey

@Composable
fun MarsRoverNavDisplay(modifier: Modifier = Modifier) {
    val focusManager = LocalFocusManager.current
    val backStack = rememberNavBackStack(Home)

    NavDisplay(
        backStack = backStack,
        modifier = modifier
            .clickable(
                interactionSource = null,
                indication = null,
                onClick = { focusManager.clearFocus() },
            )
            .imePadding(),
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = entryProvider {
            entry<Home> {
                HomeScreenRoute(onStartSetup = {
                    backStack.add(Setup)
                })
            }
            entry<Setup> {
                SetupScreenRoute(
                    onNavigate = { navKey -> backStack.add(navKey) },
                )
            }
            entry<Movements> { movements ->
                MovementsScreenRoute(
                    movements = movements,
                    onNavigateBack = { backStack.removeLastOrNull() },
                )
            }
        },
    )
}
