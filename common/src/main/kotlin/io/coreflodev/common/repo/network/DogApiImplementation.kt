package io.coreflodev.common.repo.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class DogApiImplementation(
    private val client: HttpClient
) : DogApi {
    override suspend fun getDogList(page: Int): List<Dog> =
        client.get("https://api.thedogapi.com/v1/images/search/?limit=10" + if (page != 0) "&page=$page" else "").call.body()

    override suspend fun getDog(id: String): Dog =
        client.get("https://api.thedogapi.com/v1/images/$id").call.body()
}
