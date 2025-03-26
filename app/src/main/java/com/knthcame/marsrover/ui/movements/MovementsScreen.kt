package com.knthcame.marsrover.ui.movements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.knthcame.marsrover.ui.theme.MarsRoverTheme

@Composable
fun MovementsScreenRoute(onNavigateBack: () -> Unit) {
    MovementsScreen(onNavigateBack = onNavigateBack)
}

@Composable
private fun MovementsScreen(onNavigateBack: () -> Unit) {
    Scaffold(topBar = {
        MovementsTopBar(onNavigateBack = onNavigateBack)
    }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(8.dp),
        ) {
            Text("Placeholder")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MovementsTopBar(onNavigateBack: () -> Unit) {
    TopAppBar(
        title = {
            Text("Input movements")
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.AutoMirrored.Default.ArrowBack, "Navigate back arrow")
            }
        })
}

@Composable
@Preview
fun MovementsScreenPreview() {
    MarsRoverTheme {
        MovementsScreen(onNavigateBack = { })
    }
}