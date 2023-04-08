package io.coreflodev.dog.common.di

import android.content.Context
import io.coreflodev.dog.common.nav.Navigation
import io.coreflodev.dog.common.repo.dog.DogRepository
import io.coreflodev.dog.common.repo.dog.RestApiDogRepository
import io.coreflodev.dog.common.repo.dog.network.DogApi
import io.coreflodev.dog.common.repo.dog.network.DogApiImplementation
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestPipeline
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Provides

abstract class AppModule {

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
    fun provideNavigation(context: Context) = Navigation(context)
}
