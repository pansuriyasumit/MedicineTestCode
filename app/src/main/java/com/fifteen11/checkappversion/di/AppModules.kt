package com.fifteen11.checkappversion.di

import android.content.Context
import androidx.room.Room
import com.fifteen11.checkappversion.data.remote.api.MedicineApiService
import com.fifteen11.checkappversion.data.local.MedicineDao
import com.fifteen11.checkappversion.data.local.MedicineDatabase
import com.fifteen11.checkappversion.data.local.repository.MedicineLocalRepository
import com.fifteen11.checkappversion.data.local.repository.MedicineLocalRepositoryImpl
import com.fifteen11.checkappversion.data.remote.repository.MedicineDataRepository
import com.fifteen11.checkappversion.data.remote.repository.MedicineDataRepositoryImpl
import com.fifteen11.checkappversion.utils.AppConstant
import com.fifteen11.checkappversion.utils.AppConstant.Companion.CONNECT_TIMEOUT
import com.fifteen11.checkappversion.utils.AppConstant.Companion.READ_TIMEOUT
import com.fifteen11.checkappversion.utils.AppConstant.Companion.WRITE_TIMEOUT
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModules {

    @Singleton
    @Provides
    fun provideRetrofit(
        moshi: Moshi,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit
            .Builder()
            .client(okHttpClient)
            .baseUrl(AppConstant.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Singleton
    @Provides
    fun provideMedicineAPI(retrofit: Retrofit): MedicineApiService {
        return retrofit.create(MedicineApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient
        .Builder().apply {
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            addInterceptor(loggingInterceptor)
        }.build()


    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideRepository(
        apiService: MedicineApiService,
    ): MedicineDataRepository {
        return MedicineDataRepositoryImpl(
            medicineAPI = apiService
        )
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MedicineDatabase {
        return Room.databaseBuilder(
            context,
            MedicineDatabase::class.java,
            "MedicineDB"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideMedicineDao(db: MedicineDatabase): MedicineDao {
        return db.medicineDao()
    }


    @Singleton
    @Provides
    fun provideMedicineRepo(medicineDao: MedicineDao) =
        MedicineLocalRepositoryImpl(medicineDao = medicineDao) as MedicineLocalRepository
}