package io.coreflodev.dog.details.arch

import io.coreflodev.dog.details.domain.Action
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class DetailsActionReducer {
    operator fun invoke(): (Flow<DetailsInput>) -> Flow<Action> = { flow ->
        flow.map { input ->
            when (input) {
                DetailsInput.RetryClicked -> Action.InitialAction
            }
        }
            .onStart { emit(Action.InitialAction) }
    }
}
