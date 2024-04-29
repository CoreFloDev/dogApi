package io.coreflodev.dog.details.arch

import io.coreflodev.common.arch.UseCaseAggregator
import io.coreflodev.dog.details.domain.Action
import io.coreflodev.dog.details.domain.DisplayDogDetailsUseCase
import io.coreflodev.dog.details.domain.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.shareIn

class DetailsUseCaseAggregator(
    private val displayDogDetailsUseCase: DisplayDogDetailsUseCase
) : UseCaseAggregator<Action, Result> {

    override fun execute(scope: CoroutineScope): (Flow<Action>) -> Flow<Result> = { stream ->
        val upstream = stream.shareIn(scope, SharingStarted.Eagerly, 1)

        upstream.filterIsInstance<Action.InitialAction>().let(displayDogDetailsUseCase())
    }
}
