package io.coreflodev.dog.details.arch

import io.coreflodev.common.arch.ScreenInput

sealed class DetailsInput : ScreenInput {
    data object RetryClicked : DetailsInput()
}
