package com.sukitha.ey.eygiphy.data.remote

import com.sukitha.ey.eygiphy.BuildConfig
import com.sukitha.ey.eygiphy.data.remote.data.GiphyDto
import com.sukitha.ey.eygiphy.data.remote.data.GiphyResponse
import com.sukitha.ey.eygiphy.util.ALL_GIPHY_PAGE_SIZE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApi {
    @GET("trending")
    suspend fun getTrendingGiphy(
        @Query(
            "api_key"
        ) apiKey: String = BuildConfig.API_KEY,
        @Query("limit") pageSize: Int = ALL_GIPHY_PAGE_SIZE,
        @Query("rating") rating: String = "g",
        @Query("offset") offset: Int
    ): Response<GiphyResponse>

    @GET("search?lang=en&rating=g")
    suspend fun getGiphy(
        @Query(
            "api_key"
        ) apiKey: String = BuildConfig.API_KEY,
        @Query("limit") pageSize: Int = ALL_GIPHY_PAGE_SIZE,
        @Query("q") query: String = "",
        @Query("offset") offset: Int
    ): Response<GiphyResponse>
}