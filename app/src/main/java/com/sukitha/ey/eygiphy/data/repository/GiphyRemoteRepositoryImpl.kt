package com.sukitha.ey.eygiphy.data.repository

import com.sukitha.ey.eygiphy.data.mappers.toDomain
import com.sukitha.ey.eygiphy.domain.data.Giphy
import com.sukitha.ey.eygiphy.domain.repository.GiphyRemoteRepository
import com.sukitha.ey.eygiphy.domain.util.ApiResult
import com.sukitha.ey.eygiphy.data.remote.GiphyApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GiphyRemoteRepositoryImpl(private val api: GiphyApi) : GiphyRemoteRepository {
    override fun getTrendingGiphy(offset: Int): Flow<ApiResult<List<Giphy>>> {
        return try {
            flow {
                emit(ApiResult.Success(
                    data = api.getTrendingGiphy(offset = offset).body()?.toDomain()
                ))
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
                emit(ApiResult.Success(
                    data = api.getGiphy(query = query, offset = offset).body()?.toDomain()
                ))
            }
        } catch (e: Exception) {
            flow {
                emit(ApiResult.Error(message = e.localizedMessage))
            }
        }
    }
}