package com.eric.composearch.di

import com.eric.composearch.api.RequestInterceptor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {
    @Binds
    abstract fun bindRequestInterceptor(reqInterceptor: RequestInterceptor): Interceptor
}