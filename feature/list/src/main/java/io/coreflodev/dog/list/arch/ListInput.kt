package io.coreflodev.dog.list.arch

import io.coreflodev.dog.common.arch.ScreenInput

sealed class ListInput : ScreenInput {
    data class PictureClicked(val id: String) : ListInput()
    object RetryClicked : ListInput()
}
