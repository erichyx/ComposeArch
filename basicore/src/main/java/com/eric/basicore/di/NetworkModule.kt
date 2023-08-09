package com.eric.basicore.di

import android.content.Context
import com.eric.basicore.gson.DoubleAdapter
import com.eric.basicore.gson.IntegerAdapter
import com.eric.basicore.gson.LongAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideOkHttpClient(
        @ApplicationContext appContext: Context,
        requestInterceptor: Interceptor,
        networkConfig: NetworkConfig
    ): OkHttpClient {
        //设置缓存目录和大小
        val cacheSize = 256 shl 20 // 256 MB
        val cacheFile = File(appContext.cacheDir, "responses")
        val cache = Cache(cacheFile, cacheSize.toLong())

        return OkHttpClient.Builder().apply {
            connectTimeout(15, TimeUnit.SECONDS)
            readTimeout(20, TimeUnit.SECONDS)
            writeTimeout(20, TimeUnit.SECONDS)
            cache(cache)
            addInterceptor(requestInterceptor)
            if (networkConfig.debugMode) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }
        }.build()
    }

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(Int::class.java, IntegerAdapter())
            .registerTypeAdapter(Int::class.javaPrimitiveType, IntegerAdapter())
            .registerTypeAdapter(Long::class.java, LongAdapter())
            .registerTypeAdapter(Long::class.javaPrimitiveType, LongAdapter())
            .registerTypeAdapter(Double::class.java, DoubleAdapter())
            .registerTypeAdapter(Double::class.javaPrimitiveType, DoubleAdapter())
            .create()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson, networkConfig: NetworkConfig): Retrofit {
        return Retrofit.Builder().baseUrl(networkConfig.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

}
