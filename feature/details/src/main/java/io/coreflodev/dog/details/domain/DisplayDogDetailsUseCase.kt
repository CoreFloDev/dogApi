package io.coreflodev.dog.details.domain

import io.coreflodev.common.repo.DogRepository
import io.coreflodev.common.repo.network.Dog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class DisplayDogDetailsUseCase(
    private val repo: DogRepository,
    private val id: String
) {
    operator fun invoke(): (Flow<Action>) -> Flow<Result> = { flow ->
        flow.flatMapLatest {
            repo.getDog(id)
                .map<Dog, Result> { dog ->
                    val firstInfo = dog.breeds.firstOrNull()
                    Result.UiUpdate.Display(
                        name = firstInfo?.name ?: "",
                        temperament = firstInfo?.temperament ?: "",
                        origin = firstInfo?.origin ?: "",
                        wikiUrl = firstInfo?.wiki ?: "",
                        image = dog.image
                    )
                }
                .catch { emit(Result.UiUpdate.Retry) }
                .onStart { emit(Result.UiUpdate.Loading) }
        }
    }
}
