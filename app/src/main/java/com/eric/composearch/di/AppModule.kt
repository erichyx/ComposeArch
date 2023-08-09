package com.eric.composearch.di

import android.content.Context
import android.content.SharedPreferences
import com.eric.basicore.di.NetworkConfig
import com.eric.composearch.BuildConfig
import com.eric.composearch.api.ApiService
import com.eric.composearch.utils.TEST_MODE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideNetworkConfig(): NetworkConfig {
        return NetworkConfig(BuildConfig.BASE_URL, TEST_MODE)
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("config", Context.MODE_PRIVATE)
    }
}
