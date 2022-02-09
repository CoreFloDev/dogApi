package io.coreflodev.dog.details.arch

import io.coreflodev.dog.common.arch.DomainResult
import io.coreflodev.dog.common.arch.Screen
import io.coreflodev.dog.details.domain.Action
import io.coreflodev.dog.details.domain.DisplayDogDetailsUseCase
import io.coreflodev.dog.details.domain.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.shareIn

@Suppress("UNCHECKED_CAST")
class DetailsScreen(
    private val displayDogDetailsUseCase: DisplayDogDetailsUseCase,
    detailsActionReducer: DetailsActionReducer,
    detailsUiReducer: DetailsUiReducer
) : Screen<DetailsInput, DetailsOutput, DetailsNavigation, Action, Result>(
    detailsActionReducer(),
    detailsUiReducer() as (Flow<DomainResult.UiUpdate>) -> Flow<DetailsOutput>
) {

    override fun output(): (Flow<Action>) -> Flow<Result> = { stream ->
        val upstream = stream.shareIn(scope, SharingStarted.Eagerly, 1)

        upstream.filterIsInstance<Action.InitialAction>().let(displayDogDetailsUseCase())
    }
}
