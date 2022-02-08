package io.coreflodev.dog.details.arch

import io.coreflodev.dog.common.arch.ScreenNavigation
import io.coreflodev.dog.common.arch.ScreenOutput

sealed class DetailsOutput : ScreenOutput {
    data class Display(val uiState: UiState = UiState.Loading) : DetailsOutput()
}

sealed class DetailsNavigation : ScreenNavigation

sealed class UiState {
    object Loading : UiState()
    object Retry : UiState()

    data class Display(
        val name: String,
        val image: String,
        val origin: String,
        val wikiUrl: String,
        val temperament: String
    ) : UiState()
}
