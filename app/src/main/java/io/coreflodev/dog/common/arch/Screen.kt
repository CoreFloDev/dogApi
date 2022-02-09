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

abstract class Screen<I : ScreenInput, O : ScreenOutput, N : ScreenNavigation>(
    private val reducingUiState: (Flow<DomainResult.UiUpdate>) -> Flow<O>,
    private val reducingNavigation: (Flow<DomainResult.Navigation>) -> Flow<N> = { flow -> flow.flatMapLatest { emptyFlow() } }
) {

    private var viewScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    protected val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    private val input: Channel<I> = Channel()
    private val output by lazy { output() }

    protected fun input(): Flow<I> =
        input
            .receiveAsFlow()
            .flowOn(Dispatchers.Default)

    protected abstract fun output(): Pair<Flow<O>, Flow<N>>

    fun terminate() {
        scope.cancel()
    }

    fun attach(): Attach<I, O, N> {
        viewScope = CoroutineScope(Dispatchers.Main)

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

    fun convertResultToOutput(): (Flow<DomainResult>) -> Pair<Flow<O>, Flow<N>> =
        { stream ->
            val upstream = stream.shareIn(scope, SharingStarted.Lazily)

            upstream.filterIsInstance<DomainResult.UiUpdate>()
                .let(reducingUiState)
                .shareIn(scope, SharingStarted.Lazily, 1) to
                    upstream.filterIsInstance<DomainResult.Navigation>()
                        .let(reducingNavigation)
        }
}
