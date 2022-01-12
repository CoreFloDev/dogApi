package io.coreflodev.dog.details.di

import dagger.Component
import io.coreflodev.dog.common.di.AppComponent

@DetailsScope
@Component(modules = [DetailsModule::class], dependencies = [AppComponent::class])
interface DetailsComponent {
    fun inject(detailsStateHolder: DetailsStateHolder)
}
