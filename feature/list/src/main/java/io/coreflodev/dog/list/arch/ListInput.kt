package io.coreflodev.dog.list.arch

import io.coreflodev.common.arch.ScreenInput

sealed class ListInput : ScreenInput {
    data class PictureClicked(val id: String) : ListInput()
    data object RetryClicked : ListInput()
}
