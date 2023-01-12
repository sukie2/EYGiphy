package com.sukitha.ey.eygiphy.domain.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Giphy(
    @PrimaryKey val id: String,
    val url: String?,
    val title: String?,
)
