package com.majestic_dev.coroutinesflow.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.majestic_dev.coroutinesflow.model.GetMessageUseCase
import com.majestic_dev.coroutinesflow.model.GetMessageUseCaseImpl
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ViewModel(
    private val getMessageUseCase: GetMessageUseCase = GetMessageUseCaseImpl()
) : ViewModel() {

    private val _visible = MutableStateFlow(true)
    val visible: StateFlow<Boolean> = _visible.asStateFlow()

    private val _message = MutableSharedFlow<String>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val message: SharedFlow<String> = _message.asSharedFlow()

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            _message.emit(getMessageUseCase())
        }
    }

    fun toggleVisibility() {
        _visible.value = _visible.value != true

        viewModelScope.launch {
            if (_visible.value) {
                eventChannel.send(Event.ShowToast("Visible"))
            } else {
                eventChannel.send(Event.ShowToast("Gone"))
            }
        }
    }

    fun changeMessage(msg: String) {
        _message.tryEmit(msg)
    }

}