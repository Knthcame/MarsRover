package com.knthcame.marsrover.ui.setup

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.knthcame.marsrover.R
import com.knthcame.marsrover.data.control.models.Coordinates
import com.knthcame.marsrover.data.control.models.Position
import com.knthcame.marsrover.ui.components.bottomsheets.DirectionModalBottomSheet
import com.knthcame.marsrover.ui.components.plateau.PlateauCanvas
import com.knthcame.marsrover.ui.setup.SetupContract.State
import com.knthcame.marsrover.ui.setup.SetupContract.UiEvent
import com.knthcame.marsrover.ui.theme.MarsRoverTheme

@Composable
fun SetupScreen(state: State, onEvent: (UiEvent) -> Unit) {
    Scaffold(
        topBar = { SetupTopBar() },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        var showBottomSheet by remember { mutableStateOf(false) }
        val focusManager = LocalFocusManager.current

        if (showBottomSheet) {
            DirectionModalBottomSheet(
                onDismiss = {
                    showBottomSheet = false
                    focusManager.clearFocus()
                },
                onSelect = { direction ->
                    onEvent(UiEvent.InitialDirectionChanged(direction))
                },
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            SetupPlateauCanvas(state)

            Text(stringResource(R.string.plateau_size))
            PlateauSizeTextFields(
                uiState = state,
                onEvent = onEvent,
            )

            Text(stringResource(R.string.initial_position))
            InitialPositionTextFields(
                uiState = state,
                onEvent = onEvent,
                onDirectionFocusChanged = { focusState -> showBottomSheet = focusState.isFocused },
            )

            Spacer(Modifier.weight(1f))
            Button(
                onClick = { onEvent(UiEvent.OnContinueClicked) },
                enabled = state.isContinueEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("setupContinueButton"),
            ) {
                Text(
                    text = stringResource(R.string.continue_button),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
private fun ColumnScope.SetupPlateauCanvas(uiState: State) {
    PlateauCanvas(
        topRightCorner = Coordinates(
            x = uiState.plateauWidth ?: 1,
            y = uiState.plateauHeight ?: 1,
        ),
        positions = listOf(
            Position(
                roverPosition = Coordinates(
                    x = uiState.initialX ?: 0,
                    y = uiState.initialY ?: 0,
                ),
                roverDirection = uiState.initialDirection,
            ),
        ),
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .heightIn(max = 200.dp),
    )
}

@Composable
private fun PlateauSizeTextFields(uiState: State, onEvent: (UiEvent) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        TextField(
            value = uiState.plateauWidth?.toString().orEmpty(),
            onValueChange = { value -> onEvent(UiEvent.PlateauWidthChanged(value)) },
            modifier = Modifier
                .weight(1f)
                .testTag("setupPlateauWidthTextField"),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            ),
            label = {
                Text(stringResource(R.string.width))
            },
        )
        TextField(
            value = uiState.plateauHeight?.toString().orEmpty(),
            onValueChange = { value -> onEvent(UiEvent.PlateauHeightChanged(value)) },
            modifier = Modifier
                .weight(1f)
                .testTag("setupPlateauHeightTextField"),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            ),
            label = {
                Text(stringResource(R.string.height))
            },
        )
    }
}

@Composable
private fun InitialPositionTextFields(
    uiState: State,
    onEvent: (UiEvent) -> Unit,
    onDirectionFocusChanged: (FocusState) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        TextField(
            value = uiState.initialX?.toString().orEmpty(),
            onValueChange = { value -> onEvent(UiEvent.InitialXChanged(value)) },
            label = {
                Text(stringResource(R.string.x_axis))
            },
            modifier = Modifier
                .weight(1f)
                .testTag("setupInitialXTextField"),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            ),
        )
        TextField(
            value = uiState.initialY?.toString().orEmpty(),
            onValueChange = { value -> onEvent(UiEvent.InitialYChanged(value)) },
            label = {
                Text(stringResource(R.string.y_axis))
            },
            modifier = Modifier
                .weight(1f)
                .testTag("setupInitialYTextField"),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            ),
        )
        TextField(
            value = uiState.initialDirection.name,
            onValueChange = {},
            label = {
                Text(stringResource(R.string.direction))
            },
            readOnly = true,
            modifier = Modifier
                .weight(1f)
                .onFocusChanged(onDirectionFocusChanged)
                .testTag("setupInitialDirectionTextField"),
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SetupTopBar() {
    TopAppBar(title = {
        Text(
            text = stringResource(R.string.initial_setup),
            modifier = Modifier.testTag("setupTopBarTitle"),
        )
    })
}

@Preview
@Composable
fun SetupScreenPreview() {
    MarsRoverTheme {
        SetupScreen(
            state = State.default(),
            onEvent = { },
        )
    }
}
