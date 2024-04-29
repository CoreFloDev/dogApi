package io.coreflodev.dog.details.arch

import io.coreflodev.dog.details.domain.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.scan

class DetailsUiReducer {

    operator fun invoke(): (Flow<Result.UiUpdate>) -> Flow<DetailsOutput> = { stream ->
        stream.scan(DetailsOutput()) { previous, new ->
            when (new) {
                is Result.UiUpdate.Display -> previous.copy(
                    uiState = UiState.Display(
                        name = new.name,
                        image = new.image,
                        wikiUrl = new.wikiUrl,
                        origin = new.origin,
                        temperament = new.temperament
                    )
                )
                Result.UiUpdate.Retry -> previous.copy(uiState = UiState.Retry)
                Result.UiUpdate.Loading -> previous.copy(uiState = UiState.Loading)
            }
        }
    }
}
