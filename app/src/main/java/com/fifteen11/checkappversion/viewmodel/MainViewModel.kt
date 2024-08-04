package com.fifteen11.checkappversion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _isLoadingState = MutableStateFlow(true)
    val isLoadingState = _isLoadingState.asStateFlow()

    init {
        viewModelScope.launch {
            delay(500L)
            _isLoadingState.value = false
        }
    }
}