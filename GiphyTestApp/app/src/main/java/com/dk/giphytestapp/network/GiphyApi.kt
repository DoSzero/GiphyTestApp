package com.dk.giphytestapp.network

import com.dk.giphytestapp.data.response.GifSearchPageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApi {

    @GET("v1/gifs/search")
    suspend fun searchGifs(
        @Query("q") searchTerm: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("rating") rating: String = "g",
        @Query("lang") lang: String = "en"
    ): GifSearchPageResponse

}
