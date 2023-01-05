package com.dk.giphytestapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dk.giphytestapp.data.entity.GifImageEntity
import com.dk.giphytestapp.data.entity.IgnoredGifEntity

@Database(
    entities = [GifImageEntity::class, IgnoredGifEntity::class],
    version = 1, exportSchema = false
)
abstract class GifDatabase : RoomDatabase() {
    abstract fun getGifDao(): GifDao

    companion object {
        const val DB_NAME = "gif.db"
    }
}
