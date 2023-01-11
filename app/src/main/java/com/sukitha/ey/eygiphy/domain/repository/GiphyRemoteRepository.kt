package com.sukitha.ey.eygiphy.domain.repository

import com.sukitha.ey.eygiphy.domain.data.Giphy
import com.sukitha.ey.eygiphy.domain.util.ApiResult
import kotlinx.coroutines.flow.Flow

interface GiphyRemoteRepository {
    fun getTrendingGiphy(): Flow<ApiResult<List<Giphy>>>
    fun getGiphy(query: String): Flow<ApiResult<List<Giphy>>>
}