package io.coreflodev.dog.list.di

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.coreflodev.dog.DogApp
import io.coreflodev.common.arch.Screen
import io.coreflodev.dog.list.arch.ListInput
import io.coreflodev.dog.list.arch.ListNavigation
import io.coreflodev.dog.list.arch.ListOutput
import io.coreflodev.dog.list.domain.Action
import io.coreflodev.dog.list.domain.Result

class ListStateHolder(app: Application) : AndroidViewModel(app) {

    val screen: Screen<ListInput, ListOutput, ListNavigation, Action, Result> = ListComponent::class.create(DogApp.appComponent(app)).screen

    override fun onCleared() {
        super.onCleared()
        screen.terminate()
    }

    class Factory(private val app: Application) : ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = ListStateHolder(app) as T
    }
}
