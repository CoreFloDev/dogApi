package io.coreflodev.dog.list.usecase

sealed class Action {
    object InitialAction : Action()
    data class OpenDetails(val id: String) : Action()
}
