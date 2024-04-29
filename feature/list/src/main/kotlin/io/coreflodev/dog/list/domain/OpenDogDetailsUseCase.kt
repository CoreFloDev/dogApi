package io.coreflodev.dog.list.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OpenDogDetailsUseCase {
    operator fun invoke(): (Flow<Action.OpenDetails>) -> Flow<Result> = { flow ->
        flow.map { Result.Navigation.OpenDetails(it.id) }
    }
}
