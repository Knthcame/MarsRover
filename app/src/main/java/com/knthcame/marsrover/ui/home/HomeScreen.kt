package com.knthcame.marsrover.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.knthcame.marsrover.R
import com.knthcame.marsrover.ui.theme.MarsRoverTheme

@Composable
fun HomeScreenRoute(onStartSetup: () -> Unit) {
    HomeScreen(
        onStartSetup = onStartSetup,
    )
}

@Composable
private fun HomeScreen(
    onStartSetup: () -> Unit,
) {
    Scaffold(
        topBar = { HomeTopBar() },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
        ) {
            Text("Welcome!")
            Spacer(Modifier.weight(1f))
            Button(
                onClick = onStartSetup,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
            ) {
                Text(
                    text = "Start",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun HomeTopBar() {
    TopAppBar(title = {
        Text(
            text = stringResource(R.string.app_name),
            modifier = Modifier.testTag("homeTopBarTitle"),
        )
    })
}

@Preview
@Composable
private fun HomeScreenPreview() {
    MarsRoverTheme {
        Surface {
            HomeScreen(
                onStartSetup = { }
            )
        }
    }
}
