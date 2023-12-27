package io.coreflodev.common.di

import android.content.Context
import io.coreflodev.common.nav.Navigation
import io.coreflodev.common.repo.DogRepository
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@AppScope
@Component
abstract class AppComponent(
    @get:Provides val context: Context,
    @get:Provides override val nav: (context: Context) -> Navigation
) : AppModule(nav) {

    abstract val movieRepo: DogRepository

    abstract val navigation: Navigation
}
