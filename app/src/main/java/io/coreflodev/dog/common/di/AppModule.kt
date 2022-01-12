package io.coreflodev.dog.common.di

import android.content.Context
import com.example.flow_arch.common.di.AppScope
import dagger.Module
import dagger.Provides
import io.coreflodev.dog.common.repo.dog.DogRepository
import io.coreflodev.dog.common.repo.dog.RestApiDogRepository
import io.coreflodev.dog.common.repo.dog.network.DogApi
import io.coreflodev.dog.common.repo.dog.network.DogApiImplementation
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.HttpRequestPipeline
import io.ktor.client.request.header
import io.ktor.http.ContentType
import kotlinx.serialization.json.Json

@Module
class AppModule(private val context: Context) {

    @Provides
    @AppScope
    fun provideContext() = context

    @Provides
    @AppScope
    fun provideHttpClient() = HttpClient {
        install("ApiAuthentication") {
            requestPipeline.intercept(HttpRequestPipeline.State) {
                context.header("x-api-key", "0d7f4abb-30bb-4dab-9d7d-1ee9dcbe6cbe")
            }
        }
        // Json
        install(JsonFeature) {
            serializer = KotlinxSerializer(json)
            accept(ContentType.Application.Json)
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
}
