package com.sukitha.ey.eygiphy.presentation.domain.data

import com.google.gson.annotations.SerializedName

data class Giphy(
    @SerializedName("id") var title: String? = null,
    @SerializedName("url") var url: String? = null
)
