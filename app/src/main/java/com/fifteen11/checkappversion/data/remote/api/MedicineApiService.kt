package com.fifteen11.checkappversion.data.remote.api

import com.fifteen11.checkappversion.data.model.MedicineResponse
import retrofit2.Response
import retrofit2.http.GET

interface MedicineApiService {

    /**
     * Set Dynamic Header
     */
    @GET("/v3/b/668bbc3aad19ca34f8848978?meta=false")
    suspend fun getAllMedicine(): Response<MedicineResponse>
}