package com.fifteen11.checkappversion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifteen11.checkappversion.data.local.UserLoginHistoryEntity
import com.fifteen11.checkappversion.data.local.repository.MedicineLocalRepository
import com.fifteen11.checkappversion.utils.SharedPreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sharedPrefs: SharedPreferenceManager,
    private val userRepository: MedicineLocalRepository
) : ViewModel() {

    companion object {
        const val DEFAULT_USERNAME = "Admin User"
    }

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username.asStateFlow()

    fun onUsernameChanged(newUsername: String) {
        _username.value = newUsername
    }

    fun insertLoginHistory() = viewModelScope.launch(IO) {
        try {
            userRepository.insertUserLoginHistory(UserLoginHistoryEntity(userName = _username.value.ifEmpty { DEFAULT_USERNAME }))
        } catch (e: Exception) {
            // Handle error (e.g., log, show error message)
        }
    }

    fun saveUserLogin() {
        val name = _username.value.ifEmpty { DEFAULT_USERNAME }
        sharedPrefs.setIsUserLogin(true)
        sharedPrefs.setUserName(name)
    }
}