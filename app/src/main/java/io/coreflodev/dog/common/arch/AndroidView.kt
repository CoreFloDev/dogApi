package io.coreflodev.dog.common.arch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import io.coreflodev.dog.common.theme.DogApiTheme

@Composable
fun <I : ScreenInput, O : ScreenOutput, N : ScreenNavigation> AndroidView(
    initialValue: O,
    attach: Attach<I,O,N>,
    nav: ((N) -> Unit)? = null,
    ui: @Composable (O, (I) -> Unit) -> Unit
) {
    DogApiTheme {
        val (input, output, navigation) = attach

        val state = output.collectAsState(initialValue)

        ui.invoke(state.value, input)

        if (nav != null) {
            LaunchedEffect(true) {
                navigation.collect {
                    nav.invoke(it)
                }
            }
        }
    }
}
