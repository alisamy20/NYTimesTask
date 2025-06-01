package com.example.common_kotlin.base.event

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

object BookmarkUpdateBus {

    // Private shared flow for internal updates
    private val _events = MutableSharedFlow<Unit>(
        replay = 0, // Do not replay old events to new collectors
        extraBufferCapacity = 1, // Allows emitting without suspension
        onBufferOverflow = BufferOverflow.DROP_OLDEST // Drop oldest if buffer is full
    )

    // Public read-only flow for subscribers
    val events: SharedFlow<Unit> = _events

    // Public method to emit the event
    fun notifyUpdate() {
        _events.tryEmit(Unit) // Fire-and-forget, won't crash
    }
}
