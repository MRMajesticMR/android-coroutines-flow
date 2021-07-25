package com.majestic_dev.coroutinesflow.ui.main

sealed class Event {

    data class ShowToast(val text: String): Event()

}