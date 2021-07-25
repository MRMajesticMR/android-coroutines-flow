package com.majestic_dev.coroutinesflow.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModel : ViewModel() {

    private val _visible = MutableLiveData<Boolean>().apply {
        value = false
    }
    val visible: LiveData<Boolean> get() = _visible

    fun toggleVisibility() {
        _visible.value = _visible.value != true
    }

}