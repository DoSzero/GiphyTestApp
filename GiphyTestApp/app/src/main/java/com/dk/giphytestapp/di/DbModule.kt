package com.dk.giphytestapp.di

import android.content.Context
import androidx.room.Room
import com.dk.giphytestapp.data.database.GifDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, GifDatabase::class.java, GifDatabase.DB_NAME).build()

    @Provides
    @Singleton
    fun provideGifDao(db: GifDatabase) = db.getGifDao()
}
