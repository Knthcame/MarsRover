package com.knthcame.marsrover

import com.knthcame.marsrover.ui.setup.SetupViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val androidModule = module {
    viewModelOf(::SetupViewModel)
}