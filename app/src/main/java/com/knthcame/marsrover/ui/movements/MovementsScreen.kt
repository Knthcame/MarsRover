package com.knthcame.marsrover.ui.movements

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.knthcame.marsrover.R
import com.knthcame.marsrover.data.control.models.CardinalDirection
import com.knthcame.marsrover.data.control.models.Coordinates
import com.knthcame.marsrover.data.control.models.Position
import com.knthcame.marsrover.ui.theme.MarsRoverTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovementsScreenRoute(
    onNavigateBack: () -> Unit,
    viewModel: MovementsViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val roverPositions by viewModel.roverPositions.collectAsState()

    MovementsScreen(
        uiState = uiState,
        roverPositions = roverPositions,
        onNavigateBack = onNavigateBack,
        onAddMovement = { movement -> viewModel.addMovement(movement) },
        onRemoveMovement = { viewModel.removeLastMovement() },
        onConfirm = { viewModel.sendMovements() },
        onDismissOutputDialog = { viewModel.dismissOutputDialog() }
    )
}

@Composable
private fun MovementsScreen(
    uiState: MovementsUiState,
    roverPositions: List<Position>,
    onNavigateBack: () -> Unit,
    onAddMovement: (Movement) -> Unit,
    onRemoveMovement: () -> Unit,
    onConfirm: () -> Unit,
    onDismissOutputDialog: () -> Unit,
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
            PlateauCanvas(
                topRightCorner = uiState.instructions.topRightCorner,
                positions = roverPositions,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )

            PlateauCanvasLegend()

            MovementsTextField(
                uiState = uiState,
                onRemoveMovement = onRemoveMovement,
            )
            MovementsInputButtons(
                onAddMovement = onAddMovement
            )

            Spacer(Modifier.Companion.weight(1f))

            Button(
                onClick = onConfirm,
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState.instructions.movements.isNotEmpty(),
            ) {
                Text(stringResource(R.string.send_movements))
            }
        }
    }
    if (uiState.outputReceived) {
        MovementsOutputDialog(
            uiState = uiState,
            onDismissOutputDialog = onDismissOutputDialog,
        )
    }
}

@Composable
private fun MovementsTextField(
    uiState: MovementsUiState,
    onRemoveMovement: () -> Unit
) {
    TextField(
        value = uiState.instructions.movements,
        onValueChange = { },
        readOnly = true,
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(stringResource(R.string.movements))
        },
        supportingText = {
            Text(stringResource(R.string.movements_supporting_text))
        },
        trailingIcon = {
            IconButton(
                onClick = onRemoveMovement,
                enabled = uiState.instructions.movements.isNotEmpty(),
            ) {
                Icon(painterResource(R.drawable.backspace), "Backspace icon")
            }
        }
    )
}

@Composable
private fun ColumnScope.MovementsInputButtons(onAddMovement: (Movement) -> Unit) {
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
            Text(stringResource(R.string.input_movements))
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.AutoMirrored.Default.ArrowBack, "Navigate back arrow")
            }
        })
}

@Composable
private fun MovementsOutputDialog(
    uiState: MovementsUiState,
    onDismissOutputDialog: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {},
        confirmButton = {
            TextButton(onClick = onDismissOutputDialog) {
                Text(stringResource(R.string.ok))
            }
        },
        title = {
            Text("Rover's output")
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(stringResource(R.string.input))
                Text(uiState.input)
                HorizontalDivider(Modifier.fillMaxWidth())
                Text(stringResource(R.string.output))
                Text(uiState.output)
            }
        }
    )
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun MovementsScreenPreview() {
    MarsRoverTheme {
        MovementsScreen(
            uiState = MovementsUiState.default(),
            roverPositions = listOf(Position(Coordinates(2, 1), CardinalDirection.North)),
            onNavigateBack = { },
            onAddMovement = { },
            onRemoveMovement = { },
            onConfirm = { },
            onDismissOutputDialog = { },
        )
    }
}