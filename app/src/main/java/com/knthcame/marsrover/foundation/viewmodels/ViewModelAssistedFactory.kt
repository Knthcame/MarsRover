package com.knthcame.marsrover.foundation.viewmodels

import androidx.lifecycle.ViewModel

fun interface ViewModelAssistedFactory<VM : ViewModel, Arg> {
    fun create(arg: Arg): VM
}
