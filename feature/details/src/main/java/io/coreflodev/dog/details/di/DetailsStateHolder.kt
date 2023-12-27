package io.coreflodev.dog.details.di

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.coreflodev.dog.DogApp
import io.coreflodev.common.arch.Screen
import io.coreflodev.dog.details.arch.DetailsInput
import io.coreflodev.dog.details.arch.DetailsNavigation
import io.coreflodev.dog.details.arch.DetailsOutput
import io.coreflodev.dog.details.domain.Action
import io.coreflodev.dog.details.domain.Result

class DetailsStateHolder(app: Application, imageId: String) : AndroidViewModel(app) {

    val screen: Screen<DetailsInput, DetailsOutput, DetailsNavigation, Action, Result> = DetailsComponent::class.create(DogApp.appComponent(app), imageId).screen

    override fun onCleared() {
        super.onCleared()
        screen.terminate()
    }

    class Factory(private val app: Application, private val imageId: String) : ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = DetailsStateHolder(app, imageId) as T
    }
}
