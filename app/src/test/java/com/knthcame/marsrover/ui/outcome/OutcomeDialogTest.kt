package com.knthcame.marsrover.ui.outcome

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams
import org.junit.Rule
import org.junit.Test

class OutcomeDialogTest {

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_6,
        renderingMode = SessionParams.RenderingMode.SHRINK,
    )

    @Test
    fun outcomeDialog() = paparazzi.snapshot {
        OutcomeDialogPreview()
    }
}
