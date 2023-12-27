package io.coreflodev.dog.arch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import io.coreflodev.common.arch.Attach
import io.coreflodev.common.arch.ScreenInput
import io.coreflodev.common.arch.ScreenNavigation
import io.coreflodev.common.arch.ScreenOutput
import io.coreflodev.dog.theme.DogApiTheme

@Composable
fun <I : ScreenInput, O : ScreenOutput, N : ScreenNavigation> AndroidView(
    attach: Attach<I, O, N>,
    nav: ((N) -> Unit) = {},
    ui: @Composable (O, (I) -> Unit) -> Unit
) {
    DogApiTheme {
        val (input, output, navigation) = attach

        val state = output.collectAsState(null)

        state.value?.let {
            ui.invoke(it, input)
        }

        LaunchedEffect(true) {
            navigation.collect {
                nav.invoke(it)
            }
        }
    }
}
