package com.knthcame.marsrover.ui.components.bottomsheets

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams
import org.junit.Rule
import org.junit.Test

class DirectionModalBottomSheetTest {

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_6,
        renderingMode = SessionParams.RenderingMode.SHRINK,
    )

    @Test
    fun directionModalBottomSheet() = paparazzi.snapshot {
        DirectionModalBottomSheetPreview()
    }
}
