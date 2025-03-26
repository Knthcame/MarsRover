package com.knthcame.marsrover.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.knthcame.marsrover.R
import com.knthcame.marsrover.ui.theme.MarsRoverTheme

@Composable
fun HomeScreenRoute() {
    HomeScreen()
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun HomeScreen() {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = stringResource(R.string.app_name),
                    modifier = Modifier.testTag("homeTopBarTitle"),
                )
            })
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Greeting(
            name = "Android",
            modifier = Modifier
                .padding(innerPadding)
                .padding(8.dp),
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
    MarsRoverTheme {
        Surface {
            HomeScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MarsRoverTheme {
        Greeting("Android")
    }
}