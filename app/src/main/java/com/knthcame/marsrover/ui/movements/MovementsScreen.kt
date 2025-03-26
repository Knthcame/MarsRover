package com.knthcame.marsrover.ui.movements

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.knthcame.marsrover.R
import com.knthcame.marsrover.data.control.model.Movement
import com.knthcame.marsrover.ui.theme.MarsRoverTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovementsScreenRoute(
    onNavigateBack: () -> Unit,
    viewModel: MovementsViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    MovementsScreen(
        uiState = uiState,
        onNavigateBack = onNavigateBack,
        onAddMovement = { movement -> viewModel.addMovement(movement) },
        onRemoveMovement = { viewModel.removeLastMovement() },
        onConfirm = { viewModel.sendMovements() },
    )
}

@Composable
private fun MovementsScreen(
    uiState: MovementsUiState,
    onNavigateBack: () -> Unit,
    onAddMovement: (Movement) -> Unit,
    onRemoveMovement: () -> Unit,
    onConfirm: () -> Unit,
) {
    Scaffold(topBar = {
        MovementsTopBar(onNavigateBack = onNavigateBack)
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Canvas(
                Modifier
                    .background(Color.Gray) //TODO: Remove
                    .fillMaxWidth(0.7f)
                    .aspectRatio(1f)
                    .align(Alignment.CenterHorizontally),
            ) {

            }

            MovementsTextField(
                uiState = uiState,
                onRemoveMovement = onRemoveMovement,
            )
            MovementsInputButtons(
                onAddMovement = onAddMovement,
                onConfirm = onConfirm,
                uiState = uiState
            )
        }
    }
}

@Composable
private fun MovementsTextField(
    uiState: MovementsUiState,
    onRemoveMovement: () -> Unit
) {
    TextField(
        value = uiState.movements.joinToString { value -> value.code },
        onValueChange = { },
        readOnly = true,
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text("Movements")
        },
        supportingText = {
            Text("Click the buttons to input new movements or erase one.")
        },
        trailingIcon = {
            IconButton(
                onClick = onRemoveMovement,
                enabled = uiState.movements.any(),
            ) {
                Icon(painterResource(R.drawable.backspace), "Backspace icon")
            }
        }
    )
}

@Composable
private fun ColumnScope.MovementsInputButtons(
    onAddMovement: (Movement) -> Unit,
    onConfirm: () -> Unit,
    uiState: MovementsUiState
) {
    MovementInputButton(
        onClick = onAddMovement,
        movement = Movement.MoveForward,
        modifier = Modifier.Companion.align(Alignment.CenterHorizontally),
    )

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(),
    ) {
        MovementInputButton(
            onClick = onAddMovement,
            movement = Movement.RotateLeft
        )
        MovementInputButton(
            onClick = onAddMovement,
            movement = Movement.RotateRight
        )
    }

    Spacer(Modifier.Companion.weight(1f))

    Button(
        onClick = onConfirm,
        modifier = Modifier.fillMaxWidth(),
        enabled = uiState.movements.any(),
    ) {
        Text("Send movements")
    }
}

@Composable
private fun MovementInputButton(
    movement: Movement,
    onClick: (Movement) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        onClick = { onClick(movement) },
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Icon(painterResource(movement.iconId), movement.iconContentDescription)
            Text(movement.label, style = MaterialTheme.typography.labelSmall)
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
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun MovementsScreenPreview() {
    MarsRoverTheme {
        MovementsScreen(
            uiState = MovementsUiState.default(),
            onNavigateBack = { },
            onAddMovement = { },
            onRemoveMovement = { },
            onConfirm = { })
    }
}