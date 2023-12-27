package io.coreflodev.dog.list.domain

import io.coreflodev.common.arch.DomainResult
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
