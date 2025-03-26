package com.knthcame.marsrover

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.knthcame.marsrover.ui.MarsRoverNavHost
import com.knthcame.marsrover.ui.theme.MarsRoverTheme
import org.koin.compose.KoinApplication
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KoinApplication(application = {
                modules(androidModule)
            }) {
                MarsRoverTheme {
                    MarsRoverNavHost(rememberNavController())
                }
            }
        }
    }
}

