package com.dk.giphytestapp.data.database

import androidx.paging.PagingSource
import com.dk.giphytestapp.data.entity.GifImageEntity
import com.dk.giphytestapp.data.entity.IgnoredGifEntity
import com.dk.giphytestapp.data.entity.toEntity
import com.dk.giphytestapp.domain.datasource.GifLocalDataSource
import com.dk.giphytestapp.domain.model.GifImage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GifLocalDataSourceImpl @Inject constructor(
    private val gifDao: GifDao
): GifLocalDataSource {

    override suspend fun insertInitialPage(page:List<GifImage>, term:String) {
        gifDao.insertRefreshPage(page.map { it.toEntity(term) }, term)
    }

    override suspend fun insertPage(page:List<GifImage>, term:String) {
        gifDao.insertAll(page.map { it.toEntity(term) })
    }

    override fun getGifPagingSource(term:String):PagingSource<Int, GifImageEntity> {
        return gifDao.getGifPagingSource(term)
    }

    override suspend fun getLastItemOffset(term:String):Int {
        return gifDao.getLastItemOffset(term)
    }

    override suspend fun insertIgnoredGifId(id: String) {
        gifDao.insertIgnoredId(IgnoredGifEntity(id))
    }
}
