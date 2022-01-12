package io.coreflodev.dog.common.di

import com.example.flow_arch.common.di.AppScope
import dagger.Component
import io.coreflodev.dog.common.repo.dog.DogRepository

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {

    fun movieRepo(): DogRepository
}
