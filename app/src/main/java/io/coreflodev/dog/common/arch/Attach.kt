package io.coreflodev.dog.common.arch

import kotlinx.coroutines.flow.Flow

data class Attach<I : ScreenInput, O : ScreenOutput, N : ScreenNavigation>(
    val input: (I) -> Unit,
    val output: Flow<O>,
    val navigation: Flow<N>
)
