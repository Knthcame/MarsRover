package com.knthcame.marsrover.di

import com.knthcame.marsrover.data.calculation.RoverPositionCalculator
import com.knthcame.marsrover.data.calculation.RoverPositionCalculatorImpl
import com.knthcame.marsrover.data.control.repositories.RoverRepository
import com.knthcame.marsrover.data.control.repositories.RoverRepositoryImpl
import com.knthcame.marsrover.data.control.sources.FakeRoverDao
import com.knthcame.marsrover.data.control.sources.RoverDao
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
fun interface RepositoryModule {
    @Singleton
    @Binds
    fun bindRoverRepository(roverRepository: RoverRepositoryImpl): RoverRepository
}

@Module
@InstallIn(SingletonComponent::class)
fun interface DataSourceModule {
    @Singleton
    @Binds
    fun bindRoverDao(roverDao: FakeRoverDao): RoverDao
}

@Module
@InstallIn(SingletonComponent::class)
fun interface CalculatorModule {
    @Singleton
    @Binds
    fun bindRoverPositionCalculator(
        roverPositionCalculator: RoverPositionCalculatorImpl,
    ): RoverPositionCalculator
}
