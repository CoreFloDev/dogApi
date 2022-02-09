package io.coreflodev.dog.details.di

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.coreflodev.dog.DogApp
import io.coreflodev.dog.common.arch.Screen
import io.coreflodev.dog.details.arch.DetailsInput
import io.coreflodev.dog.details.arch.DetailsNavigation
import io.coreflodev.dog.details.arch.DetailsOutput
import io.coreflodev.dog.details.domain.Action
import io.coreflodev.dog.details.domain.Result
import javax.inject.Inject

class DetailsStateHolder (app: Application, imageId: String): AndroidViewModel(app) {

    @Inject
    lateinit var screen: Screen<DetailsInput, DetailsOutput, DetailsNavigation, Action, Result>

    init {
        DaggerDetailsComponent.builder()
            .appComponent(DogApp.appComponent(app))
            .detailsModule(DetailsModule(imageId))
            .build()
            .inject(this)
    }

    override fun onCleared() {
        super.onCleared()
        screen.terminate()
    }

    class Factory(private val app: Application, private val imageId: String) : ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = DetailsStateHolder(app, imageId) as T
    }
}
