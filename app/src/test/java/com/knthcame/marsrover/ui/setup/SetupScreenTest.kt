package com.knthcame.marsrover.ui.setup

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import org.junit.Rule
import org.junit.Test

class SetupScreenTest {
    @get:Rule
    val paparazzi = Paparazzi(deviceConfig = DeviceConfig.PIXEL_6)

    @Test
    fun setupScreen() {
        paparazzi.snapshot {
            SetupScreenPreview()
        }
    }
}
