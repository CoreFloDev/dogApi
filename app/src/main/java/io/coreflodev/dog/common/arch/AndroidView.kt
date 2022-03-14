package io.coreflodev.dog.common.arch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import io.coreflodev.dog.common.theme.DogApiTheme

@Composable
fun <I : ScreenInput, O : ScreenOutput, N : ScreenNavigation> AndroidView(
    attach: Attach<I,O,N>,
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
