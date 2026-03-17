package com.knthcame.marsrover.foundation.coroutines

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope

interface CoroutineScopeProvider {
    val viewModel: CoroutineScope
    val events: CoroutineContext
}
