package com.sukitha.ey.eygiphy.data.remote

import com.sukitha.ey.eygiphy.BuildConfig
import com.sukitha.ey.eygiphy.data.remote.data.GiphyDto
import com.sukitha.ey.eygiphy.data.remote.data.GiphyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApi {
    @GET("trending")
    suspend fun getTrendingGiphy(
        @Query(
            "api_key"
        ) apiKey: String = BuildConfig.API_KEY,
        @Query("limit") pageSize: Int = 100,
        @Query("rating") rating: String = "g",
    ): Response<GiphyResponse>

    fun getGiphy(query: String): List<GiphyDto>
}