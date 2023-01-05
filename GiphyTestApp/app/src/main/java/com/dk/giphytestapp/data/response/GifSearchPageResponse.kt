package com.dk.giphytestapp.data.response


import com.google.gson.annotations.SerializedName

data class GifSearchPageResponse(
    @SerializedName("data")
    val content: List<GifInfoResponse?>?,
    @SerializedName("meta")
    val meta: MetaResponse?,
    @SerializedName("pagination")
    val pagination: PaginationResponse?
)
