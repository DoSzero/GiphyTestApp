package com.dk.giphytestapp.presentation.gifGrid.model

import com.dk.giphytestapp.domain.model.GifImage

data class GifImageGridItemModel(
    val id: String,
    val pagingOffset: Int,
    val previewUrl: String,
    val fullGifUrl: String
)

fun GifImage.toItemModel() = GifImageGridItemModel(
    id = id,
    pagingOffset = pagingOffset,
    previewUrl = previewUrl,
    fullGifUrl = fullGifUrl
)
