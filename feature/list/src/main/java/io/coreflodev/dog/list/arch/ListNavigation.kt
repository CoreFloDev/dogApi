package io.coreflodev.dog.list.arch

import io.coreflodev.dog.common.arch.ScreenNavigation

sealed class ListNavigation : ScreenNavigation {
    data class OpenDogDetails(val id: String) : ListNavigation()
}
