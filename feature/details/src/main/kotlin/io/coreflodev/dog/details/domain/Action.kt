package io.coreflodev.dog.details.domain

import io.coreflodev.common.arch.DomainAction

sealed class Action : DomainAction {
    data object InitialAction : Action()
}
