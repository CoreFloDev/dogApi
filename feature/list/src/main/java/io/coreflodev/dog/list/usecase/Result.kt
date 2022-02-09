package io.coreflodev.dog.list.usecase

import io.coreflodev.dog.common.arch.DomainResult
import io.coreflodev.dog.list.arch.UiDog

sealed class Result : DomainResult {
    sealed class UiUpdate : Result(), DomainResult.UiUpdate {
        object Loading : UiUpdate()
        object Error : UiUpdate()
        data class Display(val uiDogs: List<UiDog>) : UiUpdate()
    }

    sealed class Navigation : Result(), DomainResult.Navigation {
        data class OpenDetails(val id: String) : Navigation()
    }
}
