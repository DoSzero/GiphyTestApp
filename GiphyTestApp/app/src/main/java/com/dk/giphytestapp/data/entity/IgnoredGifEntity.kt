package com.dk.giphytestapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ignored_gifs")
data class IgnoredGifEntity(
    @PrimaryKey
    val id: String
)
