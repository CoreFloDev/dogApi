package io.coreflodev.common.repo

import io.coreflodev.common.repo.network.Dog
import kotlinx.coroutines.flow.Flow

interface DogRepository {
    fun getDogList(page: Int): Flow<List<Dog>>
    fun getDog(id: String): Flow<Dog>
}
