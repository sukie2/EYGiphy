package com.sukitha.ey.eygiphy.domain.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Giphy(
    val id: String?,
    val url: String?,
    val title: String?,
    val isFavourite: Boolean? = false,
    @PrimaryKey val key: Int? = null
)
