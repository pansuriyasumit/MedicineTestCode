package com.fifteen11.checkappversion.di

import android.content.Context
import com.fifteen11.checkappversion.utils.SharedPreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ManagerModules {

    @Singleton
    @Provides
    fun providePreferenceManager(@ApplicationContext context: Context): SharedPreferenceManager =
        SharedPreferenceManager(context)

}