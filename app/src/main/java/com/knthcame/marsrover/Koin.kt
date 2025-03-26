package com.knthcame.marsrover

import com.knthcame.marsrover.ui.setup.SetupViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val androidModule = module {
    factory { CoroutineScope(Dispatchers.Main.immediate + SupervisorJob()) }

    viewModelOf(::SetupViewModel)
}