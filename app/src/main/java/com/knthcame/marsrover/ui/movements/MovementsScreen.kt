package com.knthcame.marsrover.ui.movements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.knthcame.marsrover.R
import com.knthcame.marsrover.data.control.models.CardinalDirection
import com.knthcame.marsrover.data.control.models.Coordinates
import com.knthcame.marsrover.data.control.models.Instructions
import com.knthcame.marsrover.data.control.models.Position
import com.knthcame.marsrover.ui.MOVEMENTS_TEXT_FIELD_TAG
import com.knthcame.marsrover.ui.MOVEMENTS_TEXT_FIELD_TRAILING_ICON_TAG
import com.knthcame.marsrover.ui.MOVEMENTS_TOP_BAR_TITLE_TAG
import com.knthcame.marsrover.ui.SEND_MOVEMENTS_BUTTON_TAG
import com.knthcame.marsrover.ui.addMovementButtonTag
import com.knthcame.marsrover.ui.components.plateau.PlateauCanvas
import com.knthcame.marsrover.ui.components.plateau.PlateauCanvasLegend
import com.knthcame.marsrover.ui.movements.MovementsContract.State
import com.knthcame.marsrover.ui.movements.MovementsContract.UiEvent
import com.knthcame.marsrover.ui.outcome.OutcomeDialog
import com.knthcame.marsrover.ui.theme.MarsRoverTheme

@Composable
fun MovementsScreen(state: State, onPushEvent: (UiEvent) -> Unit) {
    Scaffold(
        topBar = {
            MovementsTopBar(onNavigateBack = { onPushEvent(UiEvent.NavigateBackClick) })
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            PlateauCanvas(
                topRightCorner = state.instructions.topRightCorner,
                positions = state.predictedPositions,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .heightIn(max = 200.dp),
            )

            PlateauCanvasLegend()

            MovementsTextField(state = state, onPushEvent = onPushEvent)
            MovementsInputButtons(onPushEvent = onPushEvent)

            Spacer(Modifier.weight(1f))

            Button(
                onClick = { onPushEvent(UiEvent.SendMovements) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(SEND_MOVEMENTS_BUTTON_TAG),
                enabled = state.instructions.movements.isNotEmpty(),
            ) {
                Text(stringResource(R.string.send_movements))
            }
        }
    }
    if (state.outputReceived) {
        OutcomeDialog(
            input = state.input,
            output = state.output,
            onDismiss = { onPushEvent(UiEvent.DismissOutputDialog) },
        )
    }
}

@Composable
private fun MovementsTextField(state: State, onPushEvent: (UiEvent) -> Unit) {
    TextField(
        value = state.instructions.movements,
        onValueChange = { },
        readOnly = true,
        modifier = Modifier
            .fillMaxWidth()
            .testTag(MOVEMENTS_TEXT_FIELD_TAG),
        label = {
            Text(stringResource(R.string.movements))
        },
        supportingText = {
            Text(stringResource(R.string.movements_supporting_text), maxLines = 1)
        },
        trailingIcon = {
            IconButton(
                onClick = { onPushEvent(UiEvent.RemoveLastMovement) },
                enabled = state.instructions.movements.isNotEmpty(),
                modifier = Modifier.testTag(MOVEMENTS_TEXT_FIELD_TRAILING_ICON_TAG),
            ) {
                Icon(painterResource(R.drawable.backspace), "Backspace icon")
            }
        },
    )
}

@Composable
private fun ColumnScope.MovementsInputButtons(onPushEvent: (UiEvent) -> Unit) {
    MovementInputButton(
        movement = Movement.MoveForward,
        onPushEvent = onPushEvent,
        modifier = Modifier.align(Alignment.CenterHorizontally),
    )

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(),
    ) {
        MovementInputButton(
            onPushEvent = onPushEvent,
            movement = Movement.RotateLeft,
        )
        MovementInputButton(
            onPushEvent = onPushEvent,
            movement = Movement.RotateRight,
        )
    }
}

@Composable
private fun MovementInputButton(
    movement: Movement,
    onPushEvent: (UiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        onClick = { onPushEvent(UiEvent.AddMovement(movement)) },
        modifier = modifier.testTag(addMovementButtonTag(movement)),
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
            Text(
                text = stringResource(R.string.input_movements),
                modifier = Modifier.testTag(MOVEMENTS_TOP_BAR_TITLE_TAG),
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(painterResource(R.drawable.outline_arrow_back_24), "Navigate back arrow")
            }
        },
    )
}

@Composable
@Preview
internal fun MovementsScreenPreview() {
    MarsRoverTheme {
        MovementsScreen(
            state = State(
                instructions = Instructions(
                    topRightCorner = Coordinates(5, 5),
                    roverPosition = Coordinates(2, 1),
                    roverDirection = CardinalDirection.North,
                    movements = "MRMLM",
                ),
                input = "",
                output = "",
                outputReceived = false,
                predictedPositions = listOf(
                    Position(Coordinates(2, 1), CardinalDirection.North),
                    Position(Coordinates(2, 2), CardinalDirection.North),
                    Position(Coordinates(2, 2), CardinalDirection.East),
                    Position(Coordinates(3, 2), CardinalDirection.East),
                    Position(Coordinates(3, 2), CardinalDirection.North),
                    Position(Coordinates(3, 3), CardinalDirection.North),
                ),
            ),
            onPushEvent = { },
        )
    }
}
