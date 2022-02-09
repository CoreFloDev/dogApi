package io.coreflodev.dog.details.di

import dagger.Module
import dagger.Provides
import io.coreflodev.dog.common.arch.DomainResult
import io.coreflodev.dog.common.arch.Screen
import io.coreflodev.dog.common.repo.dog.DogRepository
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

@Module
class DetailsModule(private val imageId: String) {

    @Provides
    @DetailsScope
    fun provideScreen(displayDogDetailsUseCase: DisplayDogDetailsUseCase): Screen<DetailsInput, DetailsOutput, DetailsNavigation, Action, Result> =
        Screen(DetailsActionReducer()(), DetailsUseCaseAggregator(displayDogDetailsUseCase), DetailsUiReducer()() as (Flow<DomainResult.UiUpdate>) -> Flow<DetailsOutput>)

    @Provides
    @DetailsScope
    fun provideDisplayDogDetailsUseCase(repository: DogRepository) = DisplayDogDetailsUseCase(repository, imageId)
}
