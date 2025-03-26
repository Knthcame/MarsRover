package com.knthcame.marsrover.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
data object Movements : TopLevelDestination

@Composable
fun MarsRoverNavHost(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navHostController,
        startDestination = Home,
        modifier = modifier,
    ) {
        composable<Home> {
            HomeScreenRoute(onStartSetup = {
                navHostController.navigate(Setup)
            })
        }
        composable<Setup> {
            SetupScreenRoute(onSetupCompleted = {
                navHostController.navigate(Movements)
            })
        }
        composable<Movements> {
            MovementsScreenRoute(onNavigateBack = {
                navHostController.popBackStack()
            })
        }
    }
}