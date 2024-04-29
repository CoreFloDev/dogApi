package io.coreflodev.common.repo

import io.coreflodev.common.repo.network.Dog
import io.coreflodev.common.repo.network.DogApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RestApiDogRepository(
    private val api: DogApi
) : DogRepository {
    override fun getDogList(page: Int): Flow<List<Dog>> = flow {
        emit(api.getDogList(page))
    }

    override fun getDog(id: String): Flow<Dog> = flow {
        emit(api.getDog(id))
    }
}
