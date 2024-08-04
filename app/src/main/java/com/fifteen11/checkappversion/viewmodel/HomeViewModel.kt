package com.fifteen11.checkappversion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifteen11.checkappversion.data.GreetingEnums
import com.fifteen11.checkappversion.data.model.MedicineResponse
import com.fifteen11.checkappversion.data.remote.repository.MedicineDataRepositoryImpl
import com.fifteen11.checkappversion.utils.SharedPreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.time.Clock
import java.time.LocalTime
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val medicineDataRepository: MedicineDataRepositoryImpl,
    private val sharedPreferenceManager: SharedPreferenceManager
) : ViewModel() {

    data class UiState(
        val medicines: MedicineResponse = MedicineResponse(),
        val isLoading: Boolean = false,
        val error: String? = null
    )

    private val _uiState = MutableStateFlow(UiState())
    val medicines: StateFlow<UiState>
        get() = _uiState.asStateFlow()

    init {
        fetchMedicines()
    }

    fun getGreetingMessage(clock: Clock = Clock.systemDefaultZone()): String {
        val currentHour = LocalTime.now(clock).hour

        val greeting = when (currentHour) {
            in 0..11 -> GreetingEnums.GOOD_MORNING.greeting
            in 12..17 -> GreetingEnums.GOOD_AFTERNOON.greeting
            in 18..23 -> GreetingEnums.GOOD_EVENING.greeting
            else -> GreetingEnums.GOOD_NIGHT.greeting
        }

        return greeting
    }

    fun getUserPreferenceData(): String? {
        return sharedPreferenceManager.getUserName()
    }

    fun fetchMedicines() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val response = medicineDataRepository.getMedicinesFormAPI()
                _uiState.value = _uiState.value.copy(medicines = response, isLoading = false)
            } catch (e: IOException) {
                _uiState.value = _uiState.value.copy(error = "Network error", isLoading = false)
            } catch (e: HttpException) {
                _uiState.value = _uiState.value.copy(error = "API error", isLoading = false)
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value.copy(error = "An unexpected error occurred", isLoading = false)
            }
        }
    }

    fun clearUserData() {
        sharedPreferenceManager.clearPreferenceData()
    }
}