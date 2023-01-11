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
        @Query("limit") pageSize: Int = 50,
        @Query("rating") rating: String = "g",
    ): Response<GiphyResponse>

    @GET("search?lang=en&offset=0&rating=g")
    suspend fun getGiphy(
        @Query(
            "api_key"
        ) apiKey: String = BuildConfig.API_KEY,
        @Query("limit") pageSize: Int = 50,
        @Query("q") query: String = "",
    ): Response<GiphyResponse>
}