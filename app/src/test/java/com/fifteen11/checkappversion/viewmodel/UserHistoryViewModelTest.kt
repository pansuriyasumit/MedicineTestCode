package com.fifteen11.checkappversion.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fifteen11.checkappversion.data.local.UserLoginHistoryEntity
import com.fifteen11.checkappversion.data.local.repository.MedicineLocalRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UserHistoryViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: UserHistoryViewModel
    private val repository = mockk<MedicineLocalRepository>()

    @Before
    fun setup() {
        viewModel = UserHistoryViewModel(repository)
    }

    @Test
    fun testGetUserLoginHistory() = runTest {
        val history = listOf<UserLoginHistoryEntity>()
        coEvery { repository.getUserLoginHistory() } returns flowOf(history)

        viewModel.getUserLoginHistory()

        assertThat(viewModel.loginHistory.first()).isEqualTo(history)
    }
}
