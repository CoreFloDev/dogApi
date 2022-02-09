package io.coreflodev.dog.list.arch

import io.coreflodev.dog.list.domain.Action
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class ListActionReducer {
    operator fun invoke(): (Flow<ListInput>) -> Flow<Action> = { flow ->
        flow.map { input ->
            when (input) {
                is ListInput.PictureClicked -> Action.OpenDetails(input.id)
                ListInput.RetryClicked -> Action.InitialAction
            }
        }
            .onStart { emit(Action.InitialAction) }
    }
}
