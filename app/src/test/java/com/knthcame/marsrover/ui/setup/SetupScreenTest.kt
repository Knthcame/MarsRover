package com.knthcame.marsrover.ui.setup

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.knthcame.marsrover.ui.theme.MarsRoverTheme
import org.junit.Rule
import org.junit.Test

class SetupScreenTest {
    @get:Rule
    val paparazzi = Paparazzi(deviceConfig = DeviceConfig.PIXEL_6)

    @Test
    fun setupScreen() {
        paparazzi.snapshot {
            MarsRoverTheme {
                SetupScreen(
                    uiState = SetupUIState.default(),
                    onEvent = { },
                    onSetupCompleted = { },
                )
            }
        }
    }
}
