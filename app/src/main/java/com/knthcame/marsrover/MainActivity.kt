package com.knthcame.marsrover

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.knthcame.marsrover.ui.MarsRoverNavHost
import com.knthcame.marsrover.ui.theme.MarsRoverTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarsRoverTheme {
                MarsRoverNavHost(rememberNavController())
            }
        }
    }
}

