package com.dk.giphytestapp.di

import com.dk.giphytestapp.BuildConfig
import com.dk.giphytestapp.network.GiphyApi
import com.dk.giphytestapp.network.GiphyAuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideGiphyApi(retrofit: Retrofit): GiphyApi {
        return retrofit.create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(converterFactory: GsonConverterFactory, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.giphy.com/")
            .addConverterFactory(converterFactory)
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideGiphyAuthInterceptor(): GiphyAuthInterceptor {
        return GiphyAuthInterceptor()
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideOkHttpAuthClient(
        loggingInterceptor: HttpLoggingInterceptor,
        giphyAuthInterceptor: GiphyAuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(giphyAuthInterceptor)
            .build()
    }
}
