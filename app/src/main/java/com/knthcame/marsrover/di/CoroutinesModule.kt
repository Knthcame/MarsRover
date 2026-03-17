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

    // TODO: Remove once all viewModels are migrated.
    companion object {

        @Provides
        fun providesDispatcher(): CoroutineDispatcher = Dispatchers.Default

        @Provides
        fun providesCoroutineScope(dispatcher: CoroutineDispatcher): CoroutineScope =
            CoroutineScope(SupervisorJob() + dispatcher)
    }

    @Binds
    fun bindCoroutineScopeProvider(impl: DefaultCoroutineScopeProvider): CoroutineScopeProvider
}
