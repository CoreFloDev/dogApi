package io.coreflodev.dog.details.arch

import io.coreflodev.dog.common.arch.Screen
import io.coreflodev.dog.details.usecase.Action
import io.coreflodev.dog.details.usecase.DisplayDogDetailsUseCase
import io.coreflodev.dog.details.usecase.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.shareIn

class DetailsScreen(
    private val displayDogDetailsUseCase: DisplayDogDetailsUseCase
) : Screen<DetailsInput, DetailsOutput, DetailsNavigation>() {

    override fun output() = input()
        .let(inputToAction())
        .let { stream ->
            val upstream = stream.shareIn(scope, SharingStarted.Eagerly, 1)

            upstream.filterIsInstance<Action.InitialAction>().let(displayDogDetailsUseCase())
        }
        .let(convertResultToOutput(scope))

    companion object {
        fun inputToAction(): (Flow<DetailsInput>) -> Flow<Action> = { flow ->
            flow.map { input ->
                when (input) {
                    DetailsInput.RetryClicked -> Action.InitialAction
                }
            }
                .onStart { emit(Action.InitialAction) }
        }

        fun convertResultToOutput(clear: CoroutineScope): (Flow<Result>) -> Pair<Flow<DetailsOutput>, Flow<DetailsNavigation>> = { stream ->
            val upstream = stream.shareIn(clear, SharingStarted.Lazily)

            upstream.filterIsInstance<Result.UiUpdate>()
                .let(reducingUiState())
                .shareIn(clear, SharingStarted.Lazily, 1) to
                    upstream.filterIsInstance<Result.Navigation>()
                        .let(reducingNavigation())
        }

        private fun reducingUiState(): (Flow<Result.UiUpdate>) -> Flow<DetailsOutput.Display> = { stream ->
            stream.scan(DetailsOutput.Display()) { previous, new ->
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

        private fun reducingNavigation(): (Flow<Result.Navigation>) -> Flow<DetailsNavigation> = { stream ->
            stream.flatMapLatest { emptyFlow() }
        }
    }
}
