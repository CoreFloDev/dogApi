package io.coreflodev.dog.list.di

import dagger.Component
import io.coreflodev.dog.common.di.AppComponent

@ListScope
@Component(modules = [ListModule::class], dependencies = [AppComponent::class])
interface ListComponent {
    fun inject(listStateHolder: ListStateHolder)
}
