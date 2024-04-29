package io.coreflodev.dog.list.domain

import app.cash.turbine.test
import io.coreflodev.dog.common.repo.dog.network.Dog
import io.coreflodev.dog.common.repo.dog.DogRepository
import io.coreflodev.dog.common.repo.dog.network.Breed
import io.coreflodev.dog.list.arch.UiDog
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class DisplayDogListUseCaseTest {

    private val repo: DogRepository = mockk()

    private val useCase = DisplayDogListUseCase(repo)

    @Test
    fun `test loading displayed`() = TestScope().runTest {
        every { repo.getDogList(1) } returns emptyFlow()

        flowOf(Action.InitialAction)
            .let(useCase())
            .test {
                assertEquals(Result.UiUpdate.Loading, awaitItem())
                awaitComplete()
            }
    }

    @Test
    fun `test no network`() = TestScope().runTest {
        every { repo.getDogList(1) } returns flow { throw Exception() }

        flowOf(Action.InitialAction)
            .let(useCase())
            .test {
                assertEquals(Result.UiUpdate.Loading, awaitItem())
                assertEquals(Result.UiUpdate.Error, awaitItem())
                awaitComplete()
            }
    }

    @Test
    fun `test list displayed`() = TestScope().runTest {
        every { repo.getDogList(1) } returns flowOf(listOf(Dog("id", "image", listOf(Breed(name = "name")))))

        flowOf(Action.InitialAction)
            .let(useCase())
            .test {
                assertEquals(Result.UiUpdate.Loading, awaitItem())
                assertEquals(Result.UiUpdate.Display(listOf(UiDog("id", "image", "name"))), awaitItem())
                awaitComplete()
            }
    }
}
