package io.coreflodev.dog.list.arch

import io.coreflodev.common.arch.ScreenOutput

data class ListOutput(val state: ScreenState = ScreenState.Loading) : ScreenOutput

sealed class ScreenState {
    data object Loading : ScreenState()
    data object Retry : ScreenState()
    data class Display(val list: List<UiDog>) : ScreenState()
}

data class UiDog(val id: String, val image: String, val name: String)
