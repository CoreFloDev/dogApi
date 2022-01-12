package io.coreflodev.dog.common.arch

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

interface ScreenView<I : ScreenInput, O : ScreenOutput> {

    fun inputs(): Flow<I> = emptyFlow()

    fun render(output: O)
}
