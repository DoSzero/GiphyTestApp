package com.dk.giphytestapp.domain.interactor

import androidx.paging.*
import com.dk.giphytestapp.GifSearchRemoteMediator
import com.dk.giphytestapp.data.database.GifLocalDataSourceImpl
import com.dk.giphytestapp.data.entity.GifImageEntity
import com.dk.giphytestapp.domain.datasource.GifRemoteDataSource
import com.dk.giphytestapp.domain.model.GifImage
import com.dk.giphytestapp.domain.model.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Singleton
class GifInteractor @Inject constructor(
    private val gifLocalDataSourceImpl: GifLocalDataSourceImpl,
    private val gifRemoteDataSource: GifRemoteDataSource
) {

    fun getPaging(term: String, initialRefresh: Boolean): Flow<PagingData<GifImage>> {
        return Pager(
            config = PagingConfig(
                pageSize = 25
            ),

            remoteMediator = GifSearchRemoteMediator(
                term,
                initialRefresh,
                gifRemoteDataSource,
                gifLocalDataSourceImpl
            )

        ) {
            gifLocalDataSourceImpl.getGifPagingSource(term)
        }.flow.map {
                paging: PagingData<GifImageEntity> -> paging.map { it.toDomain() }
        }
    }

    suspend fun ignoreGif(gifId: String) {
        gifLocalDataSourceImpl.insertIgnoredGifId(gifId)
    }
}
