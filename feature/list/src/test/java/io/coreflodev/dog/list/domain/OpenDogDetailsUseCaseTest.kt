package io.coreflodev.dog.list.domain

import app.cash.turbine.test
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class OpenDogDetailsUseCaseTest {

    private val useCase = OpenDogDetailsUseCase()

    @Test
    fun `nominal test case`() = TestScope().runTest {
        flowOf(Action.OpenDetails(ID))
            .let(useCase())
            .test {
                assertEquals(Result.Navigation.OpenDetails(ID), awaitItem())
                awaitComplete()
            }
    }

    companion object {
        private const val ID = "id"
    }
}
