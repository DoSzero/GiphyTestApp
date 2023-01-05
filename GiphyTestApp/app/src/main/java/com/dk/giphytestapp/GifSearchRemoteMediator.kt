package com.dk.giphytestapp

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.dk.giphytestapp.data.entity.GifImageEntity
import com.dk.giphytestapp.domain.datasource.GifLocalDataSource
import com.dk.giphytestapp.domain.datasource.GifRemoteDataSource
import com.dk.giphytestapp.domain.model.GifImage
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class GifSearchRemoteMediator(
    private val term: String,
    private val initialRefresh: Boolean,
    private val remoteDataSource: GifRemoteDataSource,
    private val localDataSource: GifLocalDataSource
) : RemoteMediator<Int, GifImageEntity>() {

    override suspend fun initialize(): InitializeAction {
        return if (initialRefresh) InitializeAction.LAUNCH_INITIAL_REFRESH else InitializeAction.SKIP_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int,GifImageEntity>):MediatorResult {
        try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    localDataSource.getLastItemOffset(term)
                }
            }

            val pagingData = remoteDataSource.searchGifs(term, state.config.pageSize, getOffset(loadKey))
            insertPageData(loadType, pagingData)

            return MediatorResult.Success(
                endOfPaginationReached = pagingData.isEmpty()
            )

        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

    private fun getOffset(loadKey: Int?) = loadKey?.plus(1) ?: 0

    private suspend fun insertPageData(loadType: LoadType, pagingData: List<GifImage>) {
        if (loadType == LoadType.REFRESH) {
            localDataSource.insertInitialPage(pagingData, term)
        } else {
            localDataSource.insertPage(pagingData, term)
        }
    }
}
