package io.coreflodev.dog.list.arch

import io.coreflodev.dog.common.arch.Screen
import io.coreflodev.dog.list.usecase.Action
import io.coreflodev.dog.list.usecase.Result
import io.coreflodev.dog.list.usecase.DisplayDogListUseCase
import io.coreflodev.dog.list.usecase.OpenDogDetailsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.shareIn

class ListScreen(
    private val displayDogListUseCase: DisplayDogListUseCase,
    private val openDogDetailsUseCase: OpenDogDetailsUseCase
) : Screen<ListInput, ListOutput>() {

    override fun output(): Flow<ListOutput> = input.receiveAsFlow()
        .let(inputToAction())
        .let { stream ->
            val upstream = stream.shareIn(scope, SharingStarted.Eagerly, 1)

            listOf(
                upstream.filterIsInstance<Action.InitialAction>().let(displayDogListUseCase()),
                upstream.filterIsInstance<Action.OpenDetails>().let(openDogDetailsUseCase())
            )
                .merge()
        }
        .let(convertResultToOutput(scope))

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

        fun convertResultToOutput(clear: CoroutineScope) : (Flow<Result>) -> Flow<ListOutput> = { stream ->
            val upstream = stream.shareIn(clear, SharingStarted.Lazily)

            listOf(
                upstream.filterIsInstance<Result.UiUpdate>()
                    .let(reducingUiState())
                    .shareIn(clear, SharingStarted.Lazily, 1),
                upstream.filterIsInstance<Result.Navigation>()
                    .let(reducingNavigation())
            ).merge()
        }

        private fun reducingUiState() :(Flow<Result.UiUpdate>) -> Flow<ListOutput.Display> = { stream ->
            stream.scan(ListOutput.Display()) { previous, new ->
                when (new) {
                    is Result.UiUpdate.Display -> previous.copy(state = ScreenState.Display(new.uiDogs))
                    Result.UiUpdate.Error -> previous.copy(state = ScreenState.Retry)
                    Result.UiUpdate.Loading -> previous.copy(state = ScreenState.Loading)
                }
            }
        }

        private fun reducingNavigation() :(Flow<Result.Navigation>) -> Flow<ListOutput> = { stream ->
            stream.map {navigation ->
                when (navigation) {
                    is Result.Navigation.OpenDetails -> ListOutput.OpenDogDetails(navigation.id)
                }
            }
        }
    }
}
