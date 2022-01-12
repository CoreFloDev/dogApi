package io.coreflodev.dog.common.repo.dog

import io.coreflodev.dog.common.repo.dog.network.Dog
import io.coreflodev.dog.common.repo.dog.network.DogApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface DogRepository {
    fun getDogList(page: Int): Flow<List<Dog>>

    fun getDog(id: String): Flow<Dog>
}
