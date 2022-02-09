package io.coreflodev.dog.details.arch

import io.coreflodev.dog.common.arch.DomainResult
import io.coreflodev.dog.common.arch.Screen
import io.coreflodev.dog.details.usecase.Action
import io.coreflodev.dog.details.usecase.DisplayDogDetailsUseCase
import io.coreflodev.dog.details.usecase.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn

@Suppress("UNCHECKED_CAST")
class DetailsScreen(
    private val displayDogDetailsUseCase: DisplayDogDetailsUseCase,
    detailsUiReducer: DetailsUiReducer
) : Screen<DetailsInput, DetailsOutput, DetailsNavigation, Result>(
    detailsUiReducer() as (Flow<DomainResult.UiUpdate>) -> Flow<DetailsOutput>
) {

    override fun output() = input()
        .let(inputToAction())
        .let { stream ->
            val upstream = stream.shareIn(scope, SharingStarted.Eagerly, 1)

            upstream.filterIsInstance<Action.InitialAction>().let(displayDogDetailsUseCase())
        }

    companion object {
        fun inputToAction(): (Flow<DetailsInput>) -> Flow<Action> = { flow ->
            flow.map { input ->
                when (input) {
                    DetailsInput.RetryClicked -> Action.InitialAction
                }
            }
                .onStart { emit(Action.InitialAction) }
        }
    }
}
