package com.knthcame.marsrover.ui.movements

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.knthcame.marsrover.R
import com.knthcame.marsrover.data.control.model.Movement

val Movement.label: String
    @Composable
    get() = when (this) {
        Movement.MoveForward -> stringResource(R.string.move_forward)
        Movement.RotateLeft -> stringResource(R.string.rotate_left)
        Movement.RotateRight -> stringResource(R.string.rotate_right)
    }

val Movement.iconId: Int
    get() = when (this) {
        Movement.MoveForward -> R.drawable.toy_car
        Movement.RotateLeft -> R.drawable.rotate_left
        Movement.RotateRight -> R.drawable.rotate_right
    }

val Movement.iconContentDescription: String
    get() = when (this) {
        Movement.MoveForward -> "Mars Rover"
        Movement.RotateLeft -> "Rotate left"
        Movement.RotateRight -> "Rotate right"
    }