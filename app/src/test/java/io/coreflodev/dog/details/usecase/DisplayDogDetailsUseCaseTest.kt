package io.coreflodev.dog.details.usecase

import app.cash.turbine.test
import io.coreflodev.dog.common.repo.dog.DogRepository
import io.coreflodev.dog.common.repo.dog.network.Breed
import io.coreflodev.dog.common.repo.dog.network.Dog
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class DisplayDogDetailsUseCaseTest {

    private val repo: DogRepository = mockk()

    private val useCase = io.coreflodev.dog.details.usecase.DisplayDogDetailsUseCase(repo, DOG_ID)

    @Test
    fun `test loading displayed`() = TestScope().runTest {
        every { repo.getDog(DOG_ID) } returns emptyFlow()

        flowOf(io.coreflodev.dog.details.usecase.Action.InitialAction)
            .let(useCase())
            .test {
                assertEquals(io.coreflodev.dog.details.usecase.Result.UiUpdate.Loading, awaitItem())
                awaitComplete()
            }
    }

    @Test
    fun `test error displayed`() = TestScope().runTest {
        every { repo.getDog(DOG_ID) } returns flow { throw Exception() }

        flowOf(io.coreflodev.dog.details.usecase.Action.InitialAction)
            .let(useCase())
            .test {
                assertEquals(io.coreflodev.dog.details.usecase.Result.UiUpdate.Loading, awaitItem())
                assertEquals(io.coreflodev.dog.details.usecase.Result.UiUpdate.Retry, awaitItem())
                awaitComplete()
            }
    }

    @Test
    fun `test dog details displayed`() = TestScope().runTest {
        every { repo.getDog(DOG_ID) } returns flowOf(Dog("id", "image", listOf(Breed("name", "temp", "wiki", "origin"))))

        flowOf(io.coreflodev.dog.details.usecase.Action.InitialAction)
            .let(useCase())
            .test {
                assertEquals(io.coreflodev.dog.details.usecase.Result.UiUpdate.Loading, awaitItem())
                assertEquals(io.coreflodev.dog.details.usecase.Result.UiUpdate.Display("name", "image", "origin", "wiki", "temp"), awaitItem())
                awaitComplete()
            }
    }

    companion object {
        private const val DOG_ID = "dog_id"
    }
}
