package com.knthcame.marsrover.ui.components.selectors

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import com.knthcame.marsrover.data.control.models.CardinalDirection
import com.knthcame.marsrover.ui.components.bottomsheets.DirectionModalBottomSheet

@Composable
fun CardinalDirectionSelector(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    onSelect: (CardinalDirection) -> Unit,
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    TextField(
        value = value,
        onValueChange = {},
        label = { Text(label) },
        readOnly = true,
        modifier = modifier.onFocusChanged { showBottomSheet = it.isFocused },
    )

    if (showBottomSheet) {
        DirectionModalBottomSheet(
            onDismiss = { focusManager.clearFocus() },
            onSelect = onSelect,
        )
    }
}
