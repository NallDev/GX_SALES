package com.nalldev.gxsales.core.util

sealed class UiState<out R>{
    data class Success <out T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
    data object Loading: UiState<Nothing>()
}