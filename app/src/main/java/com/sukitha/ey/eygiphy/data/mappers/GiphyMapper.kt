package com.sukitha.ey.eygiphy.data.mappers

import com.sukitha.ey.eygiphy.data.remote.data.GiphyResponse
import com.sukitha.ey.eygiphy.domain.data.Giphy

fun GiphyResponse.toDomain(): List<Giphy> {
    val mutableGiphyList = mutableListOf<Giphy>()

    giphyList?.forEach { giphyDto ->
        mutableGiphyList.add(
            Giphy(
                id = giphyDto.id ?: "",
                title = giphyDto.title,
                url = giphyDto.imageData?.original?.url
            )
        )
    }
    return mutableGiphyList.toList()
}