package com.vikayarska.data.network

import com.vikayarska.data.BuildConfig
import com.vikayarska.data.network.api.ImageApi
import com.vikayarska.data.network.api.RandomApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideRandomApi(
        retrofit: Retrofit.Builder
    ): RandomApi {
        return retrofit
            .baseUrl(provideRandomApiUrl())
            .build().create(RandomApi::class.java)
    }

    @Singleton
    @Provides
    fun provideImageApi(
        retrofit: Retrofit.Builder
    ): ImageApi {
        return retrofit
            .baseUrl(provideCatApiUrl())
            .build().create(ImageApi::class.java)
    }

    @Provides
    fun provideRandomApiUrl() = BuildConfig.LINK_RANDOM_TEXT

    @Provides
    fun provideCatApiUrl() = BuildConfig.LINK_CAT_IMAGES

    @Provides
    @Singleton
    fun provideRetrofitBuilder(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideInterceptor() = Interceptor { chain ->
        val request = chain.request()
        chain.proceed(request)
    }
}