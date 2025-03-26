package com.knthcame.marsrover.ui.setup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.knthcame.marsrover.R
import com.knthcame.marsrover.data.control.model.CardinalDirection
import com.knthcame.marsrover.ui.theme.MarsRoverTheme
import kotlinx.coroutines.launch

@Composable
fun SetupScreenRoute(onSetupCompleted: () -> Unit) {
    var plateauSize by remember { mutableStateOf("") }
    var initialX by remember { mutableStateOf("") }
    var initialY by remember { mutableStateOf("") }
    var initialDirection: CardinalDirection? by remember { mutableStateOf(null) }

    SetupScreen(
        plateauSize = plateauSize,
        onPlateauSizeValueChanged = { value -> plateauSize = value },
        initialX = initialX,
        onInitialXChanged = { value -> initialX = value },
        initialY = initialY,
        onInitialYChanged = { value -> initialY = value },
        initialDirection = initialDirection,
        onInitialDirectionChanged = { value -> initialDirection = value },
        onSetupCompleted = onSetupCompleted,
    )
}

@Composable
private fun SetupScreen(
    plateauSize: String,
    onPlateauSizeValueChanged: (String) -> Unit,
    initialX: String,
    onInitialXChanged: (String) -> Unit,
    initialY: String,
    onInitialYChanged: (String) -> Unit,
    initialDirection: CardinalDirection?,
    onInitialDirectionChanged: (CardinalDirection) -> Unit,
    onSetupCompleted: () -> Unit,
) {
    Scaffold(
        topBar = { SetupTopBar() },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        var showBottomSheet by remember { mutableStateOf(false) }
        val focusManager = LocalFocusManager.current

        if (showBottomSheet) {
            val dismiss = {
                showBottomSheet = false
                focusManager.clearFocus()
            }
            DirectionPicker(
                onDismiss = dismiss, onSelect = { direction ->
                    onInitialDirectionChanged(direction)
                    dismiss()
                })
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(stringResource(R.string.plateau_size))
            TextField(
                value = plateauSize,
                onValueChange = onPlateauSizeValueChanged,
                modifier = Modifier.fillMaxWidth(),
            )
            Text(stringResource(R.string.initial_position))
            InitialPositionTextFields(
                initialX = initialX,
                onInitialXChanged = onInitialXChanged,
                initialY = initialY,
                onInitialYChanged = onInitialYChanged,
                initialDirection = initialDirection,
                onDirectionFocusChanged = { focusState ->
                    showBottomSheet = focusState.isFocused
                },
            )

            Spacer(Modifier.weight(1f))

            Button(
                onClick = onSetupCompleted,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
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
        )
        TextField(
            value = initialY,
            onValueChange = onInitialYChanged,
            label = {
                Text(stringResource(R.string.y_axis))
            },
            modifier = Modifier.weight(1f),
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
private fun DirectionPicker(
    onDismiss: () -> Unit,
    onSelect: (CardinalDirection) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
    ) {
        DirectionsList(
            onSelect = { value ->
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    onSelect(value)
                }
            },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
        )
    }
}

@Composable
private fun DirectionsList(
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
            var plateauSize by remember { mutableStateOf("") }
            var initialX by remember { mutableStateOf("") }
            var initialY by remember { mutableStateOf("") }
            var initialDirection: CardinalDirection? by remember { mutableStateOf(null) }

            SetupScreen(
                plateauSize = plateauSize,
                onPlateauSizeValueChanged = { value -> plateauSize = value },
                initialX = initialX,
                onInitialXChanged = { value -> initialX = value },
                initialY = initialY,
                onInitialYChanged = { value -> initialY = value },
                initialDirection = initialDirection,
                onInitialDirectionChanged = { value -> initialDirection = value },
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
            DirectionsList(onSelect = {})
        }
    }
}