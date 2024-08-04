package com.fifteen11.checkappversion

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fifteen11.checkappversion.data.model.AssociatedDrugType1Item
import com.fifteen11.checkappversion.data.model.AssociatedDrugType2Item
import com.fifteen11.checkappversion.data.model.DrugsNameItem
import com.fifteen11.checkappversion.data.model.LabsItem
import com.fifteen11.checkappversion.data.model.MedicationsClassesItem
import com.fifteen11.checkappversion.data.model.MedicationsItem
import com.fifteen11.checkappversion.data.model.MedicineResponse
import com.fifteen11.checkappversion.data.model.ProblemsItem
import com.fifteen11.checkappversion.data.remote.api.MedicineApiService
import com.fifteen11.checkappversion.data.remote.repository.MedicineDataRepository
import com.fifteen11.checkappversion.data.remote.repository.MedicineDataRepositoryImpl
import com.fifteen11.checkappversion.utils.AppConstant
import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.IOException
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class MedicineDataRepositoryTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: MedicineApiService
    private lateinit var repository: MedicineDataRepository
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url(AppConstant.BASE_URL))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        apiService = retrofit.create()
        repository = MedicineDataRepositoryImpl(apiService)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testGetMedicinesFormAPI_success() = runTest {
        val mockResponse = createMockResponse(
            status = 200,
            code = 0,
            message = "Success",
            problems = listOf(createProblemsItem())
        )

        mockWebServer.enqueue(createMockWebResponse(mockResponse, 200))

        val response = repository.getMedicinesFormAPI()
        assertThat(response).isEqualTo(mockResponse)
    }

    @Test
    fun testGetMedicinesFormAPI_failure() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(500).setBody("Error"))

        val response = repository.getMedicinesFormAPI()
        assertThat(response).isEqualTo(MedicineResponse())
    }

    @Test
    fun testGetMedicinesFormAPI_emptyResponse() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody("{}"))

        val response = repository.getMedicinesFormAPI()
        assertThat(response).isEqualTo(MedicineResponse())
    }

    @Test
    fun testGetMedicinesFormAPI_invalidJson() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody("{invalid_json}"))

        val response = repository.getMedicinesFormAPI()
        assertThat(response).isEqualTo(MedicineResponse())
    }

    @Test
    fun testGetMedicinesFormAPI_unexpectedResponse() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody("{\"unexpected_field\": \"value\"}"))

        val response = repository.getMedicinesFormAPI()
        assertThat(response).isEqualTo(MedicineResponse())
    }

    @Test
    fun testGetMedicinesFormAPI_timeout() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBodyDelay(10, java.util.concurrent.TimeUnit.SECONDS))

        try {
            repository.getMedicinesFormAPI()
            // Timeout handling
            assertThat(true).isFalse() // Fail test if no exception
        } catch (e: IOException) {
            // Expected behavior
        }
    }

    @Test
    fun testGetMedicinesFormAPI_networkError() = runTest {
        mockWebServer.shutdown()

        try {
            repository.getMedicinesFormAPI()
            assertThat(true).isFalse() // Fail test if no exception
        } catch (e: IOException) {
            // Expected behavior
        }
    }

    @Test
    fun testGetMedicinesFormAPI_unexpectedHttpStatus() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(418).setBody("I'm a teapot"))

        val response = repository.getMedicinesFormAPI()
        assertThat(response).isEqualTo(MedicineResponse())
    }

    @Test
    fun testGetMedicinesFormAPI_responseWithNullFields() = runTest {
        val mockResponse = """
            {
                "status": 200,
                "code": 0,
                "message": null,
                "problems": [
                    {
                        "id": null,
                        "type": null,
                        "medications": null,
                        "labs": null
                    }
                ]
            }
        """

        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(mockResponse))

        val response = repository.getMedicinesFormAPI()
        assertThat(response).isEqualTo(
            MedicineResponse(
                status = 200,
                code = 0,
                message = null,
                problems = listOf(
                    ProblemsItem(id = null, type = null, medications = null, labs = null)
                )
            )
        )
    }

    private fun createMockResponse(status: Int, code: Int, message: String?, problems: List<ProblemsItem>? = null): MedicineResponse {
        return MedicineResponse(
            status = status,
            code = code,
            message = message,
            problems = problems
        )
    }

    private fun createProblemsItem(): ProblemsItem {
        return ProblemsItem(
            id = 1,
            type = "TypeA",
            medications = listOf(MedicationsItem(
                    medicationsClasses = listOf(MedicationsClassesItem(
                            drugsName = listOf(DrugsNameItem(
                                    associatedDrugType1 = listOf(
                                        AssociatedDrugType1Item(name = "Drug1", dose = "10mg", strength = "Strong")),
                                    associatedDrugType2 = listOf(
                                        AssociatedDrugType2Item(name = "Drug2", dose = "20mg", strength = "Medium")
                                    )
                                )
                            )
                        )
                    )
                )
            ),
            labs = listOf(LabsItem(missingField = "Field"))
        )
    }

    private fun createMockWebResponse(response: MedicineResponse, responseCode: Int): MockResponse {
        return MockResponse()
            .setResponseCode(responseCode)
            .setBody(moshi.adapter(MedicineResponse::class.java).toJson(response))
    }
}