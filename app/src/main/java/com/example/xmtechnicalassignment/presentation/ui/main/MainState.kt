package com.example.xmtechnicalassignment.presentation.ui.main

sealed interface UiStatus {
    object Loading : UiStatus
    object Empty : UiStatus
    object Error : UiStatus
}

data class MainState(
    val status: UiStatus? = null
)
