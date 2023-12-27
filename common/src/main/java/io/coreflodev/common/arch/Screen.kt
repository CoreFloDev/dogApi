package io.coreflodev.common.arch

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

class Screen<I : ScreenInput, O : ScreenOutput, N : ScreenNavigation, A : DomainAction, R : DomainResult>(
    reducingAction: (Flow<I>) -> Flow<A>,
    useCaseAggregator: UseCaseAggregator<A, R>,
    private val reducingUiState: (Flow<DomainResult.UiUpdate>) -> Flow<O>,
    private val reducingNavigation: (Flow<DomainResult.Navigation>) -> Flow<N> = { flow -> flow.flatMapLatest { emptyFlow() } }
) {

    private var viewScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    private val input: Channel<I> = Channel()

    private val output = input
        .receiveAsFlow()
        .let(reducingAction)
        .let(useCaseAggregator.execute(scope))
        .let(convertResultToOutput())

    fun terminate() {
        scope.cancel()
    }

    fun attach(): Attach<I, O, N> {
        viewScope = CoroutineScope(Dispatchers.IO)

        val (out, nav) = output

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
