package io.coreflodev.dog.common.repo.dog

import io.coreflodev.dog.common.repo.dog.network.Dog
import kotlinx.coroutines.flow.Flow

interface DogRepository {
    fun getDogList(page: Int): Flow<List<Dog>>

    fun getDog(id: String): Flow<Dog>
}
