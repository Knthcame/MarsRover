package com.knthcame.marsrover

import com.knthcame.marsrover.data.control.repositories.RoverRepository
import com.knthcame.marsrover.data.control.repositories.RoverRepositoryImpl
import com.knthcame.marsrover.data.control.sources.FakeRoverDao
import com.knthcame.marsrover.data.control.sources.RoverDao
import com.knthcame.marsrover.ui.movements.MovementsViewModel
import com.knthcame.marsrover.ui.setup.SetupViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val androidModule = module {
    singleOf(::RoverRepositoryImpl) { bind<RoverRepository>() }
    singleOf(::FakeRoverDao) { bind<RoverDao>() }

    factory { CoroutineScope(Dispatchers.Main.immediate + SupervisorJob()) }

    viewModelOf(::SetupViewModel)
    viewModelOf(::MovementsViewModel)
}