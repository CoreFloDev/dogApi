package io.coreflodev.dog.list.arch

import io.coreflodev.dog.common.arch.ResultNavigation
import io.coreflodev.dog.common.arch.ResultUiUpdate
import io.coreflodev.dog.common.arch.Screen
import io.coreflodev.dog.list.usecase.Action
import io.coreflodev.dog.list.usecase.DisplayDogListUseCase
import io.coreflodev.dog.list.usecase.OpenDogDetailsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn

class ListScreen(
    private val displayDogListUseCase: DisplayDogListUseCase,
    private val openDogDetailsUseCase: OpenDogDetailsUseCase,
    private val listNavigationReducer: ListNavigationReducer,
    private val listUiReducer: ListUiReducer
) : Screen<ListInput, ListOutput, ListNavigation>() {

    override fun output() = input()
        .let(inputToAction())
        .let { stream ->
            val upstream = stream.shareIn(scope, SharingStarted.Eagerly, 1)

            listOf(
                upstream.filterIsInstance<Action.InitialAction>().let(displayDogListUseCase()),
                upstream.filterIsInstance<Action.OpenDetails>().let(openDogDetailsUseCase())
            )
                .merge()
        }
        .let(convertResultToOutput(listUiReducer() as (Flow<ResultUiUpdate>) -> Flow<ListOutput>, listNavigationReducer() as (Flow<ResultNavigation>) -> Flow<ListNavigation>))

    companion object {
        fun inputToAction(): (Flow<ListInput>) -> Flow<Action> = { flow ->
            flow.map { input ->
                when (input) {
                    is ListInput.PictureClicked -> Action.OpenDetails(input.id)
                    ListInput.RetryClicked -> Action.InitialAction
                }
            }
                .onStart { emit(Action.InitialAction) }
        }
    }
}
