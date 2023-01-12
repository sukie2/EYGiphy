package com.sukitha.ey.eygiphy.domain.repository

import com.sukitha.ey.eygiphy.domain.data.Giphy
import com.sukitha.ey.eygiphy.domain.util.ApiResult
import kotlinx.coroutines.flow.Flow

interface GiphyRepository {
    fun getTrendingGiphy(offset: Int): Flow<ApiResult<List<Giphy>>>
    fun getGiphy(query: String, offset: Int): Flow<ApiResult<List<Giphy>>>
    suspend fun getGiphy(): Flow<List<Giphy>>
    suspend fun insertGiphy(giphy: Giphy)
    suspend fun deleteGiphy(giphy: Giphy)
}