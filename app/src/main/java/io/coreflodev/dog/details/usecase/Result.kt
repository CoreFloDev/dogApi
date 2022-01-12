package io.coreflodev.dog.details.usecase

sealed class Result {
    sealed class UiUpdate : Result() {
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

    sealed class Navigation : Result()
}
