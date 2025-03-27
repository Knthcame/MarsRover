package com.knthcame.marsrover.ui.setup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.knthcame.marsrover.R
import com.knthcame.marsrover.data.control.models.CardinalDirection
import com.knthcame.marsrover.data.control.models.Coordinates
import com.knthcame.marsrover.data.control.models.Position
import com.knthcame.marsrover.ui.movements.PlateauCanvas
import com.knthcame.marsrover.ui.theme.MarsRoverTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SetupScreenRoute(
    onSetupCompleted: (topRightCorner: Coordinates, initialPosition: Coordinates, initialDirection: CardinalDirection) -> Unit,
    viewModel: SetupViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    SetupScreen(
        uiState = uiState,
        onEvent = { event -> viewModel.onEvent(event) },
        onSetupCompleted = {
            onSetupCompleted(
                Coordinates(
                    x = uiState.plateauWidth.toInt(),
                    y = uiState.plateauHeight.toInt()
                ),
                Coordinates(
                    uiState.initialX.toInt(),
                    uiState.initialY.toInt(),
                ),
                uiState.initialDirection,
            )
        },
    )
}

@Composable
private fun SetupScreen(
    uiState: SetupUIState,
    onEvent: (SetupUiEvent) -> Unit,
    onSetupCompleted: () -> Unit,
) {
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
                    onEvent(SetupUiEvent.InitialDirectionChanged(direction))
                },
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            PlateauCanvas(
                topRightCorner = Coordinates(
                    x = uiState.plateauWidth.toIntOrNull() ?: 1,
                    y = uiState.plateauHeight.toIntOrNull() ?: 1,
                ),
                positions = listOf(
                    Position(
                        roverPosition = Coordinates(
                            x = uiState.initialX.toIntOrNull() ?: 0,
                            y = uiState.initialY.toIntOrNull() ?: 0,
                        ),
                        roverDirection = uiState.initialDirection,
                    )
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )

            Text(stringResource(R.string.plateau_size))
            PlateauSizeTextFields(
                uiState = uiState,
                onEvent = onEvent,
            )

            Text(stringResource(R.string.initial_position))
            InitialPositionTextFields(
                initialX = uiState.initialX,
                onInitialXChanged = { value -> onEvent(SetupUiEvent.InitialXChanged(value)) },
                initialY = uiState.initialY,
                onInitialYChanged = { value -> onEvent(SetupUiEvent.InitialYChanged(value)) },
                initialDirection = uiState.initialDirection,
                onDirectionFocusChanged = { focusState ->
                    showBottomSheet = focusState.isFocused
                },
            )

            Spacer(Modifier.weight(1f))

            Button(
                onClick = onSetupCompleted,
                enabled = uiState.isContinueEnabled,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "Continue",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
            }
        }

    }
}

@Composable
private fun PlateauSizeTextFields(
    uiState: SetupUIState,
    onEvent: (SetupUiEvent) -> Unit,
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        TextField(
            value = uiState.plateauWidth,
            onValueChange = { value -> onEvent(SetupUiEvent.PlateauWidthChanged(value)) },
            modifier = Modifier.weight(1f),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            ),
            label = {
                Text(stringResource(R.string.width))
            },
        )
        TextField(
            value = uiState.plateauHeight,
            onValueChange = { value -> onEvent(SetupUiEvent.PlateauHeightChanged(value)) },
            modifier = Modifier.weight(1f),
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
    initialX: String,
    onInitialXChanged: (String) -> Unit,
    initialY: String,
    onInitialYChanged: (String) -> Unit,
    initialDirection: CardinalDirection?,
    onDirectionFocusChanged: (FocusState) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        TextField(
            value = initialX,
            onValueChange = onInitialXChanged,
            label = {
                Text(stringResource(R.string.x_axis))
            },
            modifier = Modifier.weight(1f),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            ),
        )
        TextField(
            value = initialY,
            onValueChange = onInitialYChanged,
            label = {
                Text(stringResource(R.string.y_axis))
            },
            modifier = Modifier.weight(1f),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            ),
        )
        TextField(
            value = initialDirection?.name ?: "",
            onValueChange = {},
            label = {
                Text(stringResource(R.string.direction))
            },
            readOnly = true,
            modifier = Modifier
                .weight(1f)
                .onFocusChanged(onDirectionFocusChanged),
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun DirectionModalBottomSheet(
    onDismiss: () -> Unit,
    onSelect: (CardinalDirection) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
    ) {
        DirectionsCompassButtons(
            onSelect = { value ->
                onSelect(value)
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    onDismiss()
                }
            },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
        )
    }
}

@Composable
private fun DirectionsCompassButtons(
    onSelect: (CardinalDirection) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        DirectionButton(CardinalDirection.North, onSelect)
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DirectionButton(CardinalDirection.West, onSelect)
            // Icon from https://www.flaticon.com/free-icon/cardinal_2998824?term=cardinal+directions&page=1&position=1&origin=tag&related_id=2998824
            Icon(
                painter = painterResource(R.drawable.cardinal),
                contentDescription = "Cardinal directions",
                modifier = Modifier.size(128.dp),
            )
            DirectionButton(CardinalDirection.East, onSelect)
        }
        DirectionButton(CardinalDirection.South, onSelect)
    }
}

@Composable
private fun DirectionButton(direction: CardinalDirection, onSelect: (CardinalDirection) -> Unit) {
    OutlinedButton(onClick = { onSelect(direction) }) {
        Text(direction.name)
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
private fun SetupScreenPreview() {
    MarsRoverTheme {
        Surface {
            SetupScreen(
                uiState = SetupUIState.default(),
                onEvent = { },
                onSetupCompleted = { }
            )
        }
    }
}

@Preview
@Composable
private fun DirectionPickerPreview() {
    MarsRoverTheme {
        Surface {
            DirectionsCompassButtons(onSelect = { })
        }
    }
}