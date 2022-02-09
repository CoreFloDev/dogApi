package io.coreflodev.dog.list.arch

import io.coreflodev.dog.common.arch.DomainResult
import io.coreflodev.dog.common.arch.Screen
import io.coreflodev.dog.list.domain.Action
import io.coreflodev.dog.list.domain.DisplayDogListUseCase
import io.coreflodev.dog.list.domain.OpenDogDetailsUseCase
import io.coreflodev.dog.list.domain.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.shareIn

@Suppress("UNCHECKED_CAST")
class ListScreen(
    private val displayDogListUseCase: DisplayDogListUseCase,
    private val openDogDetailsUseCase: OpenDogDetailsUseCase,
    listActionReducer: ListActionReducer,
    listNavigationReducer: ListNavigationReducer,
    listUiReducer: ListUiReducer
) : Screen<ListInput, ListOutput, ListNavigation, Action, Result>(
    listActionReducer(),
    listUiReducer() as (Flow<DomainResult.UiUpdate>) -> Flow<ListOutput>,
    listNavigationReducer() as (Flow<DomainResult.Navigation>) -> Flow<ListNavigation>
) {

    override fun output(): (Flow<Action>) -> Flow<Result> = { stream ->
        val upstream = stream.shareIn(scope, SharingStarted.Eagerly, 1)

        listOf(
            upstream.filterIsInstance<Action.InitialAction>().let(displayDogListUseCase()),
            upstream.filterIsInstance<Action.OpenDetails>().let(openDogDetailsUseCase())
        )
            .merge()
    }
}
