package com.sukitha.ey.eygiphy.data.repository

import com.sukitha.ey.eygiphy.data.local.GiphyDao
import com.sukitha.ey.eygiphy.data.mappers.toDomain
import com.sukitha.ey.eygiphy.data.remote.GiphyApi
import com.sukitha.ey.eygiphy.domain.data.Giphy
import com.sukitha.ey.eygiphy.domain.repository.GiphyRepository
import com.sukitha.ey.eygiphy.domain.util.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GiphyRepositoryImpl(private val api: GiphyApi, private val dao: GiphyDao) : GiphyRepository {
    override fun getTrendingGiphy(offset: Int): Flow<ApiResult<List<Giphy>>> {
        return try {
            flow {
                emit(
                    ApiResult.Success(
                        data = api.getTrendingGiphy(offset = offset).body()?.toDomain()
                    )
                )
            }
        } catch (e: Exception) {
            flow {
                emit(ApiResult.Error(message = e.localizedMessage))
            }
        }
    }

    override fun getGiphy(query: String, offset: Int): Flow<ApiResult<List<Giphy>>> {
        return try {
            flow {
                emit(
                    ApiResult.Success(
                        data = api.getGiphy(query = query, offset = offset).body()?.toDomain()
                    )
                )
            }
        } catch (e: Exception) {
            flow {
                emit(ApiResult.Error(message = e.localizedMessage))
            }
        }
    }

    override suspend fun getGiphy(): Flow<List<Giphy>> {
        return dao.getGiphy()
    }

    override suspend fun insertGiphy(giphy: Giphy) {
        dao.insertGiphy(giphy)
    }

    override suspend fun deleteGiphy(giphy: Giphy) {
        dao.deleteGiphy(giphy)
    }
}