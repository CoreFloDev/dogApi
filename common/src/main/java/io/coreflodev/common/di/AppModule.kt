package io.coreflodev.common.di

import io.coreflodev.common.nav.Navigation
import io.coreflodev.common.repo.DogRepository
import io.coreflodev.common.repo.RestApiDogRepository
import io.coreflodev.common.repo.network.DogApi
import io.coreflodev.common.repo.network.DogApiImplementation
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestPipeline
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Provides

abstract class AppModule(
    open val nav: () -> Navigation
) {

    @Provides
    @AppScope
    fun provideHttpClient() = HttpClient {
        install("ApiAuthentication") {
            requestPipeline.intercept(HttpRequestPipeline.State) {
                context.header("x-api-key", "0d7f4abb-30bb-4dab-9d7d-1ee9dcbe6cbe")
            }
        }
        // Json
        install(ContentNegotiation) {
            json(json)
            charset("utf-8")
        }

    }

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = false
    }

    @Provides
    @AppScope
    fun provideDogApi(httpClient: HttpClient): DogApi = DogApiImplementation(httpClient)

    @Provides
    @AppScope
    fun provideDogRepository(dogApi: DogApi): DogRepository = RestApiDogRepository(dogApi)

    @Provides
    @AppScope
    fun provideNavigation(): Navigation = nav()
}
