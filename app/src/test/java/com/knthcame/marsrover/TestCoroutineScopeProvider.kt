package com.knthcame.marsrover

import com.knthcame.marsrover.foundation.coroutines.CoroutineScopeProvider
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
class TestCoroutineScopeProvider : CoroutineScopeProvider {
    override val viewModel: CoroutineScope = CoroutineScope(UnconfinedTestDispatcher())
    override val events: CoroutineContext = UnconfinedTestDispatcher()
}
