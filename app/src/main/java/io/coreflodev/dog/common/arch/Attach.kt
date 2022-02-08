package io.coreflodev.dog.common.arch

import kotlinx.coroutines.flow.Flow

data class Attach<I : ScreenInput, O : ScreenOutput, N : ScreenNavigation>(
    val output: Flow<O>,
    val input: (I) -> Unit,
    val navigation: Flow<N>
)
