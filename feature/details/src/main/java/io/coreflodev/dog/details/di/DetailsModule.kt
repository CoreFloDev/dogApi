package io.coreflodev.dog.details.di

import dagger.Module
import dagger.Provides
import io.coreflodev.dog.common.arch.Screen
import io.coreflodev.dog.common.repo.dog.DogRepository
import io.coreflodev.dog.details.arch.DetailsInput
import io.coreflodev.dog.details.arch.DetailsNavigation
import io.coreflodev.dog.details.arch.DetailsOutput
import io.coreflodev.dog.details.arch.DetailsScreen
import io.coreflodev.dog.details.usecase.DisplayDogDetailsUseCase

@Module
class DetailsModule(private val imageId: String) {

    @Provides
    @DetailsScope
    fun provideScreen(displayDogDetailsUseCase: DisplayDogDetailsUseCase): Screen<DetailsInput, DetailsOutput, DetailsNavigation> = DetailsScreen(displayDogDetailsUseCase)

    @Provides
    @DetailsScope
    fun provideDisplayDogDetailsUseCase(repository: DogRepository) = DisplayDogDetailsUseCase(repository, imageId)
}
