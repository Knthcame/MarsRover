package com.knthcame.marsrover.ui.components.plateau

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams
import org.junit.Rule
import org.junit.Test

class PlateauCanvasTest {

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_6,
        renderingMode = SessionParams.RenderingMode.SHRINK,
    )

    @Test
    fun setup() = paparazzi.snapshot {
        PlateauCanvasSetupPreview()
    }

    @Test
    fun movements() = paparazzi.snapshot {
        PlateauCanvasMovementsPreview()
    }
}
