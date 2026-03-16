package com.knthcame.marsrover.ui.outcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.knthcame.marsrover.R
import com.knthcame.marsrover.ui.theme.MarsRoverTheme

@Composable
fun OutcomeDialog(input: String, output: String, onDismiss: () -> Unit) = AlertDialog(
    onDismissRequest = {},
    confirmButton = {
        TextButton(onClick = onDismiss) {
            Text(stringResource(R.string.ok))
        }
    },
    title = {
        Text(stringResource(R.string.rover_output_dialog_title))
    },
    text = {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(stringResource(R.string.input))
            Text(input)
            HorizontalDivider(Modifier.fillMaxWidth())
            Text(stringResource(R.string.output))
            Text(output, modifier = Modifier.testTag("movementsOutputDialogOutputText"))
        }
    },
)

@Preview
@Composable
fun OutcomeDialogPreview() {
    MarsRoverTheme {
        OutcomeDialog(
            input = "input information",
            output = "Output from Mars' rover",
            onDismiss = { },
        )
    }
}
