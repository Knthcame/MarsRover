package com.knthcame.marsrover.ui.navigation

import androidx.lifecycle.ViewModel

fun interface ViewModelAssistedFactory<VM : ViewModel, Arg> {
    fun create(arg: Arg): VM
}