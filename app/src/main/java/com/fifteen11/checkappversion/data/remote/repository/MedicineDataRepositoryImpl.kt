package com.fifteen11.checkappversion.data.remote.repository

import com.fifteen11.checkappversion.data.remote.api.MedicineApiService
import com.fifteen11.checkappversion.data.model.MedicineResponse
import javax.inject.Inject

class MedicineDataRepositoryImpl @Inject constructor(
    private val medicineAPI: MedicineApiService,
) : MedicineDataRepository {

    override suspend fun getMedicinesFormAPI(): MedicineResponse {
        val medicines = medicineAPI.getAllMedicine()
        return medicines.body() ?: MedicineResponse()
    }
}