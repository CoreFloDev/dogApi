package io.coreflodev.dog.list.arch

import io.coreflodev.dog.common.arch.ScreenOutput

sealed class ListOutput : ScreenOutput {

    data class OpenDogDetails(val id: String) : ListOutput()

    data class Display(val state: ScreenState = ScreenState.Loading) : ListOutput()
}

sealed class ScreenState {
    object Loading : ScreenState()
    object Retry : ScreenState()
    data class Display(val list: List<UiDog>) : ScreenState()
}

data class UiDog(val id: String, val image: String, val name: String)
