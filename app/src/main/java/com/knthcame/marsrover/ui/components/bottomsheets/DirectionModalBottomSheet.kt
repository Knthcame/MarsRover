package com.knthcame.marsrover.ui.components.bottomsheets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.knthcame.marsrover.R
import com.knthcame.marsrover.data.control.models.CardinalDirection
import com.knthcame.marsrover.ui.theme.MarsRoverTheme
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DirectionModalBottomSheet(onDismiss: () -> Unit, onSelect: (CardinalDirection) -> Unit) {
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
    OutlinedButton(
        onClick = { onSelect(direction) },
        modifier = Modifier.testTag("modalSheet${direction}DirectionButton"),
    ) {
        Text(direction.name)
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
