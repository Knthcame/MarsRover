package com.knthcame.marsrover.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.knthcame.marsrover.data.control.models.CardinalDirection
import com.knthcame.marsrover.ui.home.HomeScreenRoute
import com.knthcame.marsrover.ui.movements.MovementsScreenRoute
import com.knthcame.marsrover.ui.setup.SetupScreenRoute
import kotlinx.serialization.Serializable

@Serializable
sealed interface TopLevelDestination

@Serializable
data object Home : TopLevelDestination

@Serializable
data object Setup : TopLevelDestination

@Serializable
data class Movements(
    val plateauHeight: Int,
    val plateauWidth: Int,
    val initialPositionX: Int,
    val initialPositionY: Int,
    val initialDirection: CardinalDirection,
) : TopLevelDestination

@Composable
fun MarsRoverNavHost(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current

    NavHost(
        navController = navHostController,
        startDestination = Home,
        modifier = modifier
            .clickable(
                interactionSource = null,
                indication = null,
                onClick = { focusManager.clearFocus() }
            )
            .imePadding(),
    ) {
        composable<Home> {
            HomeScreenRoute(onStartSetup = {
                navHostController.navigate(Setup)
            })
        }
        composable<Setup> {
            SetupScreenRoute(onSetupCompleted = { topRightCorner, initialPosition, initialDirection ->
                navHostController.navigate(
                    Movements(
                        plateauHeight = topRightCorner.y,
                        plateauWidth = topRightCorner.x,
                        initialPositionX = initialPosition.x,
                        initialPositionY = initialPosition.y,
                        initialDirection = initialDirection,
                    )
                )
            })
        }
        composable<Movements> {
            MovementsScreenRoute(onNavigateBack = {
                navHostController.popBackStack()
            })
        }
    }
}