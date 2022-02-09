package io.coreflodev.dog.list.di

import dagger.Module
import dagger.Provides
import io.coreflodev.dog.common.arch.Screen
import io.coreflodev.dog.common.repo.dog.DogRepository
import io.coreflodev.dog.list.arch.ListInput
import io.coreflodev.dog.list.arch.ListNavigation
import io.coreflodev.dog.list.arch.ListNavigationReducer
import io.coreflodev.dog.list.arch.ListOutput
import io.coreflodev.dog.list.arch.ListScreen
import io.coreflodev.dog.list.arch.ListUiReducer
import io.coreflodev.dog.list.usecase.DisplayDogListUseCase
import io.coreflodev.dog.list.usecase.OpenDogDetailsUseCase
import io.coreflodev.dog.list.usecase.Result

@Module
class ListModule {

    @Provides
    @ListScope
    fun provideScreen(
        displayDogListUseCase: DisplayDogListUseCase,
        openDogDetailsUseCase: OpenDogDetailsUseCase
    ) : Screen<ListInput, ListOutput, ListNavigation, Result> = ListScreen(
        displayDogListUseCase,
        openDogDetailsUseCase,
        ListNavigationReducer(),
        ListUiReducer()
    )

    @Provides
    @ListScope
    fun provideDisplayDogListUseCase(repo: DogRepository) = DisplayDogListUseCase(repo)

    @Provides
    @ListScope
    fun provideOpenDogDetailsUseCase() = OpenDogDetailsUseCase()
}
