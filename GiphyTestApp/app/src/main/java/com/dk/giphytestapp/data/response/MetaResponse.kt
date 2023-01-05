package com.dk.giphytestapp.data.response


import com.google.gson.annotations.SerializedName

data class MetaResponse(
    @SerializedName("msg")
    val msg: String?,
    @SerializedName("response_id")
    val responseId: String?,
    @SerializedName("status")
    val status: Int?
)
