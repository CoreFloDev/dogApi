package io.coreflodev.dog.list.arch

import io.coreflodev.dog.list.domain.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.scan

class ListUiReducer {

    operator fun invoke()  :(Flow<Result.UiUpdate>) -> Flow<ListOutput> = { stream ->
        stream.scan(ListOutput()) { previous, new ->
            when (new) {
                is Result.UiUpdate.Display -> previous.copy(state = ScreenState.Display(new.uiDogs))
                Result.UiUpdate.Error -> previous.copy(state = ScreenState.Retry)
                Result.UiUpdate.Loading -> previous.copy(state = ScreenState.Loading)
            }
        }
    }
}
