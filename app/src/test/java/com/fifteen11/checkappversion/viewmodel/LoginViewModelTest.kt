package com.fifteen11.checkappversion.viewmodel

import com.fifteen11.checkappversion.data.local.UserLoginHistoryEntity
import com.fifteen11.checkappversion.data.local.repository.MedicineLocalRepository
import com.fifteen11.checkappversion.model.UserLogin
import com.fifteen11.checkappversion.utils.SharedPreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    private val sharedPrefs: SharedPreferenceManager = mock()
    private val userRepository: MedicineLocalRepository = mock()
    private lateinit var viewModel: LoginViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = LoginViewModel(sharedPrefs, userRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onUsernameChanged updates username`() {
        val newUsername = "testuser"
        viewModel.onUsernameChanged(newUsername)
        assertEquals(newUsername, viewModel.username.value)
    }

    @Test
    fun `insertLoginHistory inserts default username when username is empty`() = runTest {
        whenever(sharedPrefs.getUserName()).doReturn("Admin User")
        viewModel.insertLoginHistory()
        verify(userRepository).insertUserLoginHistory(any())
    }

    @Test
    fun `insertLoginHistory inserts provided username when not empty`() = runTest {
        val username = "testuser"
        viewModel.onUsernameChanged(username)
        viewModel.insertLoginHistory()
        verify(userRepository).insertUserLoginHistory(UserLoginHistoryEntity(userName = username))
    }

    @Test
    fun `saveUserLogin saves user login data`() {
        val username = "testuser"
        viewModel.onUsernameChanged(username)
        viewModel.saveUserLogin()

        val expectedUserLogin = UserLogin(username = username, isLoggedIn = true)
        verify(sharedPrefs).setIsUserLogin(expectedUserLogin.isLoggedIn)
        verify(sharedPrefs).setUserName(expectedUserLogin.username)

    }

    @Test
    fun `saveUserLogin saves default username when username is empty`() {
        whenever(sharedPrefs.getUserName()).doReturn("Admin User")
        viewModel.saveUserLogin()

        val expectedUserLogin = UserLogin(username = "Admin User", isLoggedIn = true)
        verify(sharedPrefs).setIsUserLogin(expectedUserLogin.isLoggedIn)
        verify(sharedPrefs).setUserName(expectedUserLogin.username)
    }
}