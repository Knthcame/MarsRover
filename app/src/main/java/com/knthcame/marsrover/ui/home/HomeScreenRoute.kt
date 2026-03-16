package com.knthcame.marsrover.ui.home

import androidx.compose.runtime.Composable

@Composable
fun HomeScreenRoute(onStartSetup: () -> Unit) {
    HomeScreen(
        onStartSetup = onStartSetup,
    )
}
