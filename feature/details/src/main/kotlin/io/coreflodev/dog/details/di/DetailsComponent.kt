package io.coreflodev.dog.details.di

import io.coreflodev.common.arch.Screen
import io.coreflodev.common.di.AppComponent
import io.coreflodev.dog.details.arch.DetailsInput
import io.coreflodev.dog.details.arch.DetailsNavigation
import io.coreflodev.dog.details.arch.DetailsOutput
import io.coreflodev.dog.details.domain.Action
import io.coreflodev.dog.details.domain.Result
import me.tatarka.inject.annotations.Component

@DetailsScope
@Component
abstract class DetailsComponent(
    @Component val appComponent: AppComponent,
    imageId: String
) : DetailsModule(imageId) {
    abstract val screen: Screen<DetailsInput, DetailsOutput, DetailsNavigation, Action, Result>
}
