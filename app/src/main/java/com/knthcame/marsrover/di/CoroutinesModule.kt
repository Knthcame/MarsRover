package com.knthcame.marsrover.di

import com.knthcame.marsrover.foundation.coroutines.CoroutineScopeProvider
import com.knthcame.marsrover.foundation.coroutines.DefaultCoroutineScopeProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(SingletonComponent::class)
interface CoroutinesModule {

    @Binds
    fun bindCoroutineScopeProvider(impl: DefaultCoroutineScopeProvider): CoroutineScopeProvider
}
