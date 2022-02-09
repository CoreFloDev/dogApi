package io.coreflodev.dog.list.arch

import io.coreflodev.dog.list.domain.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ListNavigationReducer {
    operator fun invoke() :(Flow<Result.Navigation>) -> Flow<ListNavigation> = { stream ->
        stream.map {navigation ->
            when (navigation) {
                is Result.Navigation.OpenDetails -> ListNavigation.OpenDogDetails(navigation.id)
            }
        }
    }
}
