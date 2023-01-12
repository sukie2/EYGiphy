package com.sukitha.ey.eygiphy.data.repository

import com.sukitha.ey.eygiphy.data.local.GiphyDao
import com.sukitha.ey.eygiphy.domain.data.Giphy
import com.sukitha.ey.eygiphy.domain.repository.GiphyLocalRepository
import kotlinx.coroutines.flow.Flow

class GiphyLocalRepositoryImpl(private val dao: GiphyDao): GiphyLocalRepository {
    override fun getGiphy(): Flow<List<Giphy>> {
        return dao.getGiphy()
    }

    override suspend fun insertGiphy(giphy: Giphy) {
        dao.insertGiphy(giphy)
    }

    override suspend fun deleteGiphy(giphy: Giphy) {
        dao.deleteGiphy(giphy)
    }

}