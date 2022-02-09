package io.coreflodev.dog.details.domain

import io.coreflodev.dog.common.arch.DomainAction

sealed class Action : DomainAction {
    object InitialAction : Action()
}
