package io.coreflodev.dog.common.arch

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class Screen<I : ScreenInput, O : ScreenOutput> {

    private var viewScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
    protected val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)

    protected val input: Channel<I> = Channel()
    private val output by lazy { output() }

    protected abstract fun output(): Flow<O>

    fun terminate() {
        scope.cancel()
    }

    fun attach(view: ScreenView<I, O>) {
        viewScope = CoroutineScope(Dispatchers.Main)

        output
            .onEach(view::render)
            .launchIn(viewScope)

        view.inputs()
            .flowOn(Dispatchers.Default)
            .onEach(input::send)
            .launchIn(viewScope)
    }

    fun detach() {
        viewScope.cancel()
    }
}
