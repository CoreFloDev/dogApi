package io.coreflodev.dog.common.di

import dagger.Component
import io.coreflodev.dog.common.nav.Navigation
import io.coreflodev.dog.common.repo.dog.DogRepository

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {

    fun movieRepo(): DogRepository

    fun navigation(): Navigation
}
