package io.coreflodev.dog.list.domain

import io.coreflodev.common.repo.network.Dog
import io.coreflodev.common.repo.DogRepository
import io.coreflodev.dog.list.arch.UiDog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class DisplayDogListUseCase(
    private val repo: DogRepository
) {
    operator fun invoke(): (Flow<Action.InitialAction>) -> Flow<Result> = { flow ->
        flow.flatMapLatest {
            repo.getDogList(1)
                .map<List<Dog>, Result> { dogs -> Result.UiUpdate.Display(dogs.map { UiDog(it.id, it.image, it.breeds.firstOrNull()?.name ?: "") }) }
                .catch { emit(Result.UiUpdate.Error) }
                .onStart { emit(Result.UiUpdate.Loading) }
        }
    }
}
