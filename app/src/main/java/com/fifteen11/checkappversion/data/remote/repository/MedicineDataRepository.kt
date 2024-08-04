package com.fifteen11.checkappversion.data.remote.repository

import com.fifteen11.checkappversion.data.model.MedicineResponse

interface MedicineDataRepository {
    suspend fun getMedicinesFormAPI(): MedicineResponse
}