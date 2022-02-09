package io.coreflodev.dog.details.usecase

import io.coreflodev.dog.common.arch.DomainResult

sealed class DetailsResult {
    sealed class UiUpdate : DetailsResult(), DomainResult.UiUpdate {
        object Loading : UiUpdate()
        object Retry : UiUpdate()
        data class Display(
            val name: String,
            val image: String,
            val origin: String,
            val wikiUrl: String,
            val temperament: String
        ) : UiUpdate()
    }
}
