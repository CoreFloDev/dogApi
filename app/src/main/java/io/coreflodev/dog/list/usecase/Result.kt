package io.coreflodev.dog.list.usecase

import io.coreflodev.dog.list.arch.UiDog

sealed class Result {

    sealed class UiUpdate : Result() {
        object Loading : UiUpdate()
        object Error : UiUpdate()
        data class Display(val uiDogs: List<UiDog>) : UiUpdate()
    }

    sealed class Navigation : Result() {
        data class OpenDetails(val id: String): Navigation()
    }
}
