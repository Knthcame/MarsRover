package com.knthcame.marsrover.ui.movements

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import org.junit.Rule
import org.junit.Test

class MovementsScreenTest {
    @get:Rule
    val paparazzi = Paparazzi(deviceConfig = DeviceConfig.PIXEL_6)

    @Test
    fun movementsScreen() = paparazzi.snapshot {
        MovementsScreenPreview()
    }
}
