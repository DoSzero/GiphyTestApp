package com.dk.giphytestapp.di

import com.dk.giphytestapp.data.remote.GifRemoteDataSourceImpl
import com.dk.giphytestapp.domain.datasource.GifRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteModule {

    @Binds
    @Singleton
    abstract fun provideGifSearchRemoteDataSource(dataSourceImpl: GifRemoteDataSourceImpl): GifRemoteDataSource

}
