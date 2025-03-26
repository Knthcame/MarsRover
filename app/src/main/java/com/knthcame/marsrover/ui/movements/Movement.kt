package com.knthcame.marsrover.ui.movements

import com.knthcame.marsrover.R
import com.knthcame.marsrover.data.control.model.Movement

val Movement.label: String
    get() = when (this) {
        Movement.MoveForward -> "Move forward (M)"
        Movement.RotateLeft -> "Rotate Left (L)"
        Movement.RotateRight -> "Rotate Right (R)"
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