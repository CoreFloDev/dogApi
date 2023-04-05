package io.coreflodev.dog.list.di

import io.coreflodev.dog.common.arch.DomainResult
import io.coreflodev.dog.common.arch.Screen
import io.coreflodev.dog.common.di.AppComponent
import io.coreflodev.dog.common.nav.Navigation
import io.coreflodev.dog.common.repo.dog.DogRepository
import io.coreflodev.dog.list.arch.ListActionReducer
import io.coreflodev.dog.list.arch.ListInput
import io.coreflodev.dog.list.arch.ListNavigation
import io.coreflodev.dog.list.arch.ListNavigationReducer
import io.coreflodev.dog.list.arch.ListOutput
import io.coreflodev.dog.list.arch.ListUiReducer
import io.coreflodev.dog.list.arch.ListUseCaseAggregator
import io.coreflodev.dog.list.domain.Action
import io.coreflodev.dog.list.domain.DisplayDogListUseCase
import io.coreflodev.dog.list.domain.OpenDogDetailsUseCase
import io.coreflodev.dog.list.domain.Result
import kotlinx.coroutines.flow.Flow
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@ListScope
@Component
abstract class ListComponent(
    @Component val appComponent: AppComponent
) {
    abstract val screen: Screen<ListInput, ListOutput, ListNavigation, Action, Result>

    @Provides
    @ListScope
    protected fun provideScreen(
        displayDogListUseCase: DisplayDogListUseCase,
        openDogDetailsUseCase: OpenDogDetailsUseCase,
        navigation: Navigation
    ) : Screen<ListInput, ListOutput, ListNavigation, Action, Result> = Screen(
        ListActionReducer()(),
        ListUseCaseAggregator(displayDogListUseCase, openDogDetailsUseCase),
        ListUiReducer()() as (Flow<DomainResult.UiUpdate>) -> Flow<ListOutput>,
        ListNavigationReducer(navigation)() as (Flow<DomainResult.Navigation>) -> Flow<ListNavigation>
    )

    @Provides
    @ListScope
    protected fun provideDisplayDogListUseCase(repo: DogRepository) = DisplayDogListUseCase(repo)

    @Provides
    @ListScope
    protected fun provideOpenDogDetailsUseCase() = OpenDogDetailsUseCase()
}
