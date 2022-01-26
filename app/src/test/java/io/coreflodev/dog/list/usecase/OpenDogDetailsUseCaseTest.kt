package io.coreflodev.dog.list.usecase

import app.cash.turbine.test
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class OpenDogDetailsUseCaseTest {

    private val useCase = io.coreflodev.dog.list.usecase.OpenDogDetailsUseCase()

    @Test
    fun `nominal test case`() = TestScope().runTest {
        flowOf(io.coreflodev.dog.list.usecase.Action.OpenDetails(ID))
            .let(useCase())
            .test {
                assertEquals(io.coreflodev.dog.list.usecase.Result.Navigation.OpenDetails(ID), awaitItem())
                awaitComplete()
            }
    }

    companion object {
        private const val ID = "id"
    }
}
