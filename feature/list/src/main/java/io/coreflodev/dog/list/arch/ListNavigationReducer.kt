package io.coreflodev.dog.list.arch

import io.coreflodev.common.nav.Navigation
import io.coreflodev.dog.list.domain.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow

class ListNavigationReducer(private val nav: Navigation) {
    operator fun invoke(): (Flow<Result.Navigation>) -> Flow<ListNavigation> = { stream ->
        stream.flatMapConcat { navigation ->
            flow {
                when (navigation) {
                    is Result.Navigation.OpenDetails -> nav.startDetailsActivity(navigation.id)
                }
            }
        }
    }
}
