package com.knthcame.marsrover.ui.components.plateau

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val lineColor: Color
    @Composable
    get() = MaterialTheme.colorScheme.onBackground

val initialPositionColor: Color
    @Composable
    get() = MaterialTheme.colorScheme.onBackground

val roverColor: Color
    @Composable
    get() = MaterialTheme.colorScheme.primary
