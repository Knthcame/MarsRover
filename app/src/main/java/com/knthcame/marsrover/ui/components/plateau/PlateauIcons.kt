package com.knthcame.marsrover.ui.components.plateau

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import com.knthcame.marsrover.R

val roverIcon: ImageBitmap
    @Composable
    get() = ImageBitmap.imageResource(R.drawable.navigation)
