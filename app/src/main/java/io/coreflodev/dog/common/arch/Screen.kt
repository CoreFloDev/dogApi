package io.coreflodev.dog.common.arch

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

open class Screen<I : ScreenInput, O : ScreenOutput, N : ScreenNavigation, A : DomainAction, R : DomainResult>(
    private val reducingAction: (Flow<I>) -> Flow<A>,
    private val useCaseAggregator: UseCaseAggregator<A, R>,
    private val reducingUiState: (Flow<DomainResult.UiUpdate>) -> Flow<O>,
    private val reducingNavigation: (Flow<DomainResult.Navigation>) -> Flow<N> = { flow -> flow.flatMapLatest { emptyFlow() } }
) {

    private var viewScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    private val input: Channel<I> = Channel()

    fun terminate() {
        scope.cancel()
    }

    fun attach(): Attach<I, O, N> {
        viewScope = CoroutineScope(Dispatchers.Main)

        val (out, nav) = input
            .receiveAsFlow()
            .flowOn(Dispatchers.Default)
            .let(reducingAction)
            .let(useCaseAggregator.execute(scope))
            .let(convertResultToOutput())

        return Attach(
            output = out,
            input = { data -> viewScope.launch { input.send(data) } },
            navigation = nav
        )
    }

    fun detach() {
        viewScope.cancel()
    }

    private fun convertResultToOutput(): (Flow<R>) -> Pair<Flow<O>, Flow<N>> =
        { stream ->
            val upstream = stream.shareIn(scope, SharingStarted.Lazily)

            upstream.filterIsInstance<DomainResult.UiUpdate>()
                .let(reducingUiState)
                .shareIn(scope, SharingStarted.Lazily, 1) to
                    upstream.filterIsInstance<DomainResult.Navigation>()
                        .let(reducingNavigation)
        }
}
