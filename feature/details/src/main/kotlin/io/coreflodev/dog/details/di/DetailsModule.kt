package io.coreflodev.dog.details.di

import io.coreflodev.common.arch.DomainResult
import io.coreflodev.common.arch.Screen
import io.coreflodev.common.repo.DogRepository
import io.coreflodev.dog.details.arch.DetailsActionReducer
import io.coreflodev.dog.details.arch.DetailsInput
import io.coreflodev.dog.details.arch.DetailsNavigation
import io.coreflodev.dog.details.arch.DetailsOutput
import io.coreflodev.dog.details.arch.DetailsUiReducer
import io.coreflodev.dog.details.arch.DetailsUseCaseAggregator
import io.coreflodev.dog.details.domain.Action
import io.coreflodev.dog.details.domain.DisplayDogDetailsUseCase
import io.coreflodev.dog.details.domain.Result
import kotlinx.coroutines.flow.Flow
import me.tatarka.inject.annotations.Provides

abstract class DetailsModule(private val imageId: String) {

    @Provides
    @DetailsScope
    protected fun provideScreen(displayDogDetailsUseCase: DisplayDogDetailsUseCase): Screen<DetailsInput, DetailsOutput, DetailsNavigation, Action, Result> =
        Screen(DetailsActionReducer()(), DetailsUseCaseAggregator(displayDogDetailsUseCase), DetailsUiReducer()() as (Flow<DomainResult.UiUpdate>) -> Flow<DetailsOutput>)

    @Provides
    @DetailsScope
    protected fun provideDisplayDogDetailsUseCase(repository: DogRepository) = DisplayDogDetailsUseCase(repository, imageId)
}
