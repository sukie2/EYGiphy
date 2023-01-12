package com.sukitha.ey.eygiphy.domain.repository

import com.sukitha.ey.eygiphy.domain.data.Giphy
import kotlinx.coroutines.flow.Flow

interface GiphyLocalRepository {
    fun getGiphy(): Flow<List<Giphy>>

    suspend fun insertGiphy(giphy: Giphy)

    suspend fun deleteGiphy(giphy: Giphy)
}