package io.coreflodev.dog.list.usecase

import io.coreflodev.dog.common.arch.ResultNavigation
import io.coreflodev.dog.common.arch.ResultUiUpdate
import io.coreflodev.dog.list.arch.UiDog

sealed class Result {
    sealed class UiUpdate : Result(), ResultUiUpdate {
        object Loading : UiUpdate()
        object Error : UiUpdate()
        data class Display(val uiDogs: List<UiDog>) : UiUpdate()
    }

    sealed class Navigation : Result(), ResultNavigation {
        data class OpenDetails(val id: String) : Navigation()
    }
}
