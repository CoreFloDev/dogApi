package io.coreflodev.dog.details.arch

import io.coreflodev.common.arch.ScreenNavigation
import io.coreflodev.common.arch.ScreenOutput

data class DetailsOutput(val uiState: UiState = UiState.Loading) : ScreenOutput

sealed class DetailsNavigation : ScreenNavigation

sealed class UiState {
    data object Loading : UiState()
    data object Retry : UiState()

    data class Display(
        val name: String,
        val image: String,
        val origin: String,
        val wikiUrl: String,
        val temperament: String
    ) : UiState()
}
