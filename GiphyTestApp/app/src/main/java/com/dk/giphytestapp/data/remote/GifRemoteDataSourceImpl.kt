package com.dk.giphytestapp.data.remote

import com.dk.giphytestapp.network.GiphyApi
import com.dk.giphytestapp.data.response.GifSearchPageResponse
import com.dk.giphytestapp.domain.datasource.GifRemoteDataSource
import com.dk.giphytestapp.domain.model.GifImage
import com.dk.giphytestapp.domain.model.toDomain
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GifRemoteDataSourceImpl @Inject constructor(
    private val apiService: GiphyApi
) : GifRemoteDataSource {

    override suspend fun searchGifs(term: String, limit: Int, offset: Int): List<GifImage> {
        return apiService.searchGifs(term, limit, offset).let {
            it.content?.mapIndexedNotNull() { index, gif ->
                gif?.toDomain(getGifPagingOffset(index, it))
            } ?: listOf()
        }
    }

    private fun getGifPagingOffset(index: Int, gifResponse: GifSearchPageResponse
    ) = gifResponse.pagination?.offset?.plus(index) ?: 0

}
