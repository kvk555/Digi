package com.example.appplus.presentation

import com.example.appplus.domain.entities.ItemEntity
import com.example.appplus.domain.repository.CatalogRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.jupiter.api.extension.ExtendWith
import java.util.logging.Logger
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
class MainViewModelTest {

    @RelaxedMockK
    private lateinit var repository: CatalogRepository
    private lateinit var testDispatcher: TestDispatcher
    private lateinit var testScope: TestScope
    private lateinit var target: MainViewModel

    private val logger = Logger.getLogger(MainViewModelTest::class.java.name)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        testDispatcher = StandardTestDispatcher()
        testScope = TestScope(testDispatcher)
        target = MainViewModel(repository)
    }

    @Test
    fun loadingData_successfulLoad_updatesScreenState() {
        testScope.runTest {
            val result = listOf(
                ItemEntity(
                    text = "text",
                    image = "image",
                    confidence = 0.0f,
                    id = "id"
                )
            )
            val emissions = mutableListOf<MainViewModel.ScreenState>()

            coEvery {
                repository.getCatalog()
            } returns Result.success(result)

            val job = launch {
                target.screenState.collect {
                    emissions += it
                    logger.info("TEST: emission + $it")
                }
            }
            testDispatcher.scheduler.advanceUntilIdle()

            target.getCatalog()

            testDispatcher.scheduler.advanceUntilIdle()


            assertEquals(3, emissions.size)
            assertEquals(MainViewModel.LoadingState.INITIAL, emissions[0].loadingState)
            assertEquals(MainViewModel.LoadingState.LOADING, emissions[1].loadingState)
            assertEquals(MainViewModel.LoadingState.COMPLETE, emissions[2].loadingState)
            assertEquals(result, emissions[2].items)

            job.cancel()
        }
    }

    @Test
    fun loadingData_loadingFailure_updatesScreenState() {
        testScope.runTest {
            val emissions = mutableListOf<MainViewModel.ScreenState>()

            coEvery {
                repository.getCatalog()
            } returns Result.failure(Exception())

            val job = launch {
                target.screenState.collect {
                    emissions += it
                    logger.info("TEST: emission + $it")
                }
            }
            testDispatcher.scheduler.advanceUntilIdle()

            target.getCatalog()

            testDispatcher.scheduler.advanceUntilIdle()

            assertEquals(MainViewModel.LoadingState.INITIAL, emissions[0].loadingState)
            assertEquals(MainViewModel.LoadingState.ERROR, emissions[1].loadingState)

            job.cancel()
        }
    }
}
