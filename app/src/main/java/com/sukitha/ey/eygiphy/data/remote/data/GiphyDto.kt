package com.sukitha.ey.eygiphy.data.remote.data

import com.google.gson.annotations.SerializedName

data class GiphyDto(
    @SerializedName("id") var id: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("images") var imageData: ImagesDto? = null
)
