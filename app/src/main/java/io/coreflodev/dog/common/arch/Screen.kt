package io.coreflodev.dog.common.arch

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class Screen<I : ScreenInput, O : ScreenOutput> {

    private var viewScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
    protected val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)

    private val input: Channel<I> = Channel()
    private val output by lazy { output() }

    protected fun input(): Flow<I> =
        input
            .receiveAsFlow()
            .flowOn(Dispatchers.Default)

    protected abstract fun output(): Flow<O>

    fun terminate() {
        scope.cancel()
    }

    fun attach() : Pair<Flow<O>, (I) -> Unit> {
        viewScope = CoroutineScope(Dispatchers.Main)

        return output to { data ->
            viewScope.launch {
                input.send(data)
            }
        }
    }

    fun outputView() : Flow<O> = output

    fun detach() {
        viewScope.cancel()
    }
}
