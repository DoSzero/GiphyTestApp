package com.dk.giphytestapp.domain.datasource

import com.dk.giphytestapp.domain.model.GifImage

interface GifRemoteDataSource {

    suspend fun searchGifs(term: String, limit: Int, offset: Int): List<GifImage>

}
