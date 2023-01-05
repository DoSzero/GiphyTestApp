package com.dk.giphytestapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dk.giphytestapp.domain.model.GifImage

@Entity(tableName = "gif_images")
data class GifImageEntity(
    @PrimaryKey
    val id: String,
    val pagingOffset: Int,
    val previewUrl: String,
    val fullGifUrl: String,
    val searchTerm: String
)

fun GifImage.toEntity(term: String): GifImageEntity {
    return GifImageEntity(
        id = id,
        pagingOffset = pagingOffset,
        previewUrl = previewUrl,
        fullGifUrl = fullGifUrl,
        searchTerm = term
    )
}
