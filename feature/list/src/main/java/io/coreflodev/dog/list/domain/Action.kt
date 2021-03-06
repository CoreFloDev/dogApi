package io.coreflodev.dog.list.domain

import io.coreflodev.dog.common.arch.DomainAction

sealed class Action : DomainAction {
    object InitialAction : Action()
    data class OpenDetails(val id: String) : Action()
}
