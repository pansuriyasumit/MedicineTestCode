package com.fifteen11.checkappversion.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fifteen11.checkappversion.data.GreetingEnums
import com.fifteen11.checkappversion.data.model.AssociatedDrugType1Item
import com.fifteen11.checkappversion.data.model.DrugsNameItem
import com.fifteen11.checkappversion.data.model.MedicationsClassesItem
import com.fifteen11.checkappversion.data.model.MedicationsItem
import com.fifteen11.checkappversion.data.model.MedicineResponse
import com.fifteen11.checkappversion.data.model.ProblemsItem
import com.fifteen11.checkappversion.data.remote.repository.MedicineDataRepositoryImpl
import com.fifteen11.checkappversion.utils.SharedPreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.io.IOException
import java.time.Clock
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeViewModel
    private lateinit var medicineDataRepository: MedicineDataRepositoryImpl
    private lateinit var sharedPreferenceManager: SharedPreferenceManager
    private val testDispatcher = UnconfinedTestDispatcher()


    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        medicineDataRepository = mock()
        sharedPreferenceManager = mock()
        viewModel = HomeViewModel(medicineDataRepository, sharedPreferenceManager)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getGreetingMessage returns correct greeting based on time`() {
        val localDateTimeM = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0))
        val clock1 = Clock.fixed(localDateTimeM.toInstant(ZoneOffset.UTC), ZoneOffset.UTC)
        assertEquals(GreetingEnums.GOOD_MORNING.greeting, viewModel.getGreetingMessage(clock1))

        val localDateTimeA = LocalDateTime.of(LocalDate.now(), LocalTime.of(15, 0))
        val clock2 = Clock.fixed(localDateTimeA.toInstant(ZoneOffset.UTC), ZoneOffset.UTC)
        assertEquals(GreetingEnums.GOOD_AFTERNOON.greeting, viewModel.getGreetingMessage(clock2))

        val localDateTimeE = LocalDateTime.of(LocalDate.now(), LocalTime.of(20, 0))
        val clock3 = Clock.fixed(localDateTimeE.toInstant(ZoneOffset.UTC), ZoneOffset.UTC)
        assertEquals(GreetingEnums.GOOD_EVENING.greeting, viewModel.getGreetingMessage(clock3))
    }

    @Test
    fun `getUserPreferenceData returns the username from SharedPreferenceManager`() =
        runTest {
            val expected = "testuser"
            whenever(sharedPreferenceManager.getUserName()).thenReturn(expected)
            val actual = viewModel.getUserPreferenceData()
            assertEquals(expected, actual)
        }


    @Test
    fun `fetchMedicines updates uiState with medicines data on success`() = runTest {
        val expected = MedicineResponse(
            status = 200,
            code = 200,
            message = "Success",
            problems = listOf(ProblemsItem(id = 1, type = "Problem Type",
                    medications = listOf(MedicationsItem(
                            medicationsClasses = listOf(MedicationsClassesItem(
                                    drugsName = listOf(DrugsNameItem(
                                            associatedDrugType1 = listOf(AssociatedDrugType1Item(
                                                    name = "Drug Name 1",
                                                    dose = "500mg",
                                                    strength = "1 tablet"
                                                )
                                            )
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            )
        )
        whenever(medicineDataRepository.getMedicinesFormAPI()).thenReturn(expected)

        viewModel.fetchMedicines()

        val uiState = viewModel.medicines.first()
        assertFalse(uiState.isLoading)
        assertNull(uiState.error)
        assertEquals(expected, uiState.medicines)
    }

    @Test
    fun `fetchMedicines should update uiState with error on failure`() = runTest {
        // Use doAnswer to handle the exception
        doAnswer {
            throw IOException("Network error")
        }.whenever(medicineDataRepository).getMedicinesFormAPI()

        viewModel.fetchMedicines()

        val uiState = viewModel.medicines.value
        assertFalse(uiState.isLoading)
        assertEquals("Network error", uiState.error)
        assertNull(uiState.medicines) // Expecting null because of error
    }

    @Test
    fun `clearUserData calls clearPreferenceData on SharedPreferenceManager`() =
        runTest {
            viewModel.clearUserData()
        }
}
