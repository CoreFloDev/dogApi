package io.coreflodev.dog.details.domain

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

    private val useCase = DisplayDogDetailsUseCase(repo, DOG_ID)

    @Test
    fun `test loading displayed`() = TestScope().runTest {
        every { repo.getDog(DOG_ID) } returns emptyFlow()

        flowOf(Action.InitialAction)
            .let(useCase())
            .test {
                assertEquals(Result.UiUpdate.Loading, awaitItem())
                awaitComplete()
            }
    }

    @Test
    fun `test error displayed`() = TestScope().runTest {
        every { repo.getDog(DOG_ID) } returns flow { throw Exception() }

        flowOf(Action.InitialAction)
            .let(useCase())
            .test {
                assertEquals(Result.UiUpdate.Loading, awaitItem())
                assertEquals(Result.UiUpdate.Retry, awaitItem())
                awaitComplete()
            }
    }

    @Test
    fun `test dog details displayed`() = TestScope().runTest {
        every { repo.getDog(DOG_ID) } returns flowOf(Dog("id", "image", listOf(Breed("name", "temp", "wiki", "origin"))))

        flowOf(Action.InitialAction)
            .let(useCase())
            .test {
                assertEquals(Result.UiUpdate.Loading, awaitItem())
                assertEquals(Result.UiUpdate.Display("name", "image", "origin", "wiki", "temp"), awaitItem())
                awaitComplete()
            }
    }

    companion object {
        private const val DOG_ID = "dog_id"
    }
}
