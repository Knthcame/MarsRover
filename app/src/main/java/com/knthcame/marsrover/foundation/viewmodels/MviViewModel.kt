package com.knthcame.marsrover.foundation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

abstract class MviViewModel<State, UiEvent, Effect>(
    viewModelScope: CoroutineScope,
    eventsCoroutineContext: CoroutineContext,
) : ViewModel(viewModelScope) {

    private val _effects = MutableSharedFlow<Effect>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    private val uiEvents = Channel<UiEvent>(Channel.Factory.BUFFERED)

    abstract val state: StateFlow<State>
    val effects: SharedFlow<Effect> = _effects.asSharedFlow()

    init {
        viewModelScope.launch(eventsCoroutineContext) {
            uiEvents.consumeAsFlow().collect { uiEvent ->
                onUiEvent(uiEvent, state.value)
            }
        }
    }

    fun push(event: UiEvent) {
        uiEvents.trySend(event)
    }

    protected abstract fun onUiEvent(uiEvent: UiEvent, state: State)

    protected fun emitEffect(effect: Effect) = viewModelScope.launch {
        _effects.emit(effect)
    }
}
