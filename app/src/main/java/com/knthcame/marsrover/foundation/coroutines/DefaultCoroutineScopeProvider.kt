package com.knthcame.marsrover.foundation.coroutines

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Singleton
class DefaultCoroutineScopeProvider @Inject constructor() : CoroutineScopeProvider {
    override val viewModel = CoroutineScope(Dispatchers.Default + SupervisorJob())
    override val events = Dispatchers.IO + SupervisorJob()
}
