package com.sukitha.ey.eygiphy.data.remote.data

import com.google.gson.annotations.SerializedName

data class GiphyResponse(
    @SerializedName("data") var giphyList: List<GiphyDto>? = null,
)
