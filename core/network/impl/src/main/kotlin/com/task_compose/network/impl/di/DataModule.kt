package com.task_compose.network.impl.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.task_compose.network.api.providers.UrlProvider
import com.task_compose.network.api.services.ProductsApi
import com.task_compose.network.impl.BuildConfig
import com.task_compose.network.impl.CONTENT_TYPE
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import retrofit2.Retrofit


@Module
@ComponentScan("com.task_compose.network.impl")
class DataModule {

    @Single
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Single
    fun providesOkHttpClient(builder: OkHttpClient.Builder): OkHttpClient = builder.build()

    @Single
    fun providesOkHttpClientBuilder(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient.Builder = OkHttpClient
        .Builder()
        .apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(
                    httpLoggingInterceptor
                )
            }
        }

    @Single
    fun provideRetrofit(json: Json, okHttpClient: OkHttpClient, urlProvider: UrlProvider): Retrofit =
        Retrofit.Builder()
            .baseUrl(urlProvider.baseUrl())
            .addConverterFactory(json.asConverterFactory(CONTENT_TYPE.toMediaType()))
            .client(okHttpClient)
            .build()

    @OptIn(ExperimentalSerializationApi::class)
    @Single
    fun provideJson() = Json {
        isLenient = true
        ignoreUnknownKeys = true
        allowSpecialFloatingPointValues = true
        useArrayPolymorphism = false
        prettyPrint = true
        coerceInputValues = true
        encodeDefaults = true
        allowStructuredMapKeys = true
        explicitNulls = true
    }

    @Single
    fun provideProductsApi(retrofit: Retrofit): ProductsApi =
        retrofit.create(ProductsApi::class.java)
}
