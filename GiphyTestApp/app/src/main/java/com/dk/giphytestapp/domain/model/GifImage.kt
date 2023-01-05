package com.dk.giphytestapp.domain.model

import com.dk.giphytestapp.data.entity.GifImageEntity
import com.dk.giphytestapp.data.response.GifInfoResponse

data class GifImage(
    val id: String,
    val pagingOffset: Int,
    val previewUrl: String,
    val fullGifUrl: String
)

fun GifInfoResponse.toDomain(offset: Int): GifImage? {
    return GifImage(
        id = id ?: return null,
        previewUrl = images?.previewGif?.url ?: return null,
        fullGifUrl = images.original?.url ?: return null,
        pagingOffset = offset
    )
}

fun GifImageEntity.toDomain(): GifImage {
    return GifImage(
        id = id,
        previewUrl = previewUrl,
        fullGifUrl = fullGifUrl,
        pagingOffset = pagingOffset
    )
}
