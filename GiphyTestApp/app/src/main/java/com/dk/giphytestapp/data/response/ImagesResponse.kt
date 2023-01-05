package com.dk.giphytestapp.data.response

import com.google.gson.annotations.SerializedName

data class ImagesResponse(
    @SerializedName("original")
    val original: OriginalResponse?,
    @SerializedName("preview_gif")
    val previewGif: PreviewGifResponse?,
)
