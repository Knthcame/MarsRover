package com.knthcame.marsrover.ui.movements

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.knthcame.marsrover.R
import com.knthcame.marsrover.ui.theme.MarsRoverTheme

@Composable
fun MovementsScreenRoute(onNavigateBack: () -> Unit) {
    MovementsScreen(
        onNavigateBack = onNavigateBack,
        onConfirm = {},
    )
}

@Composable
private fun MovementsScreen(
    onNavigateBack: () -> Unit,
    onConfirm: () -> Unit,
) {
    Scaffold(topBar = {
        MovementsTopBar(onNavigateBack = onNavigateBack)
    }
    ) { innerPadding ->
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

            val movements = remember { mutableStateListOf<Char>() }

            TextField(
                value = movements.joinToString(),
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
                        onClick = {
                            movements.removeAt(movements.lastIndex)
                        },
                        enabled = movements.any(),
                    ) {
                        Icon(painterResource(R.drawable.backspace), "Backspace icon")
                    }
                }
            )
            MovementInputButton(
                onClick = {
                    movements.add('M')
                },
                iconId = R.drawable.toy_car,
                text = "Move forward (M)",
                iconContentDescription = "Mars Rover",
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {

                MovementInputButton(
                    onClick = {
                        movements.add('L')
                    },
                    iconId = R.drawable.rotate_left,
                    text = "Rotate left (L)",
                    iconContentDescription = "Rotate left",
                )

                MovementInputButton(
                    onClick = {
                        movements.add('R')
                    },
                    iconId = R.drawable.rotate_right,
                    text = "Rotate right (R)",
                    iconContentDescription = "Rotate right",
                )
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = onConfirm,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Send instructions")
            }
        }
    }
}

@Composable
private fun MovementInputButton(
    onClick: () -> Unit,
    iconId: Int,
    text: String,
    modifier: Modifier = Modifier,
    iconContentDescription: String = "",
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Icon(painterResource(iconId), iconContentDescription)
            Text(text, style = MaterialTheme.typography.labelSmall)
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
        MovementsScreen(onNavigateBack = { }, onConfirm = { })
    }
}