package com.knthcame.marsrover.di

import com.knthcame.marsrover.foundation.coroutines.CoroutineScopeProvider
import com.knthcame.marsrover.foundation.coroutines.DefaultCoroutineScopeProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CoroutinesModule {

    @Binds
    abstract fun bindCoroutineScopeProvider(
        impl: DefaultCoroutineScopeProvider,
    ): CoroutineScopeProvider
}
