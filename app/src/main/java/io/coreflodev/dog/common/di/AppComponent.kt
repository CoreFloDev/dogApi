package io.coreflodev.dog.common.di

import android.content.Context
import io.coreflodev.dog.common.nav.Navigation
import io.coreflodev.dog.common.repo.dog.DogRepository
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@AppScope
@Component
abstract class AppComponent(@get:Provides val context: Context) : AppModule() {

    abstract val movieRepo: DogRepository

    abstract val navigation: Navigation
}
