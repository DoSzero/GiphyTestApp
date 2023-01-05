package com.dk.giphytestapp.domain.datasource

import androidx.paging.PagingSource
import com.dk.giphytestapp.data.entity.GifImageEntity
import com.dk.giphytestapp.domain.model.GifImage

interface GifLocalDataSource {

    suspend fun insertInitialPage(page: List<GifImage>, term: String)
    suspend fun insertPage(page: List<GifImage>, term: String)
    fun getGifPagingSource(term: String): PagingSource<Int, GifImageEntity>
    suspend fun getLastItemOffset(term: String): Int
    suspend fun insertIgnoredGifId(id: String)
}
