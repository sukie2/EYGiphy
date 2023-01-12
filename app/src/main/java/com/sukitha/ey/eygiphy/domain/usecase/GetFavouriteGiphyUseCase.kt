package com.sukitha.ey.eygiphy.domain.usecase

import com.sukitha.ey.eygiphy.domain.data.Giphy
import com.sukitha.ey.eygiphy.domain.repository.GiphyRepository
import kotlinx.coroutines.flow.Flow

class GetFavouriteGiphyUseCase(private val repository: GiphyRepository) {
    suspend operator fun invoke(): Flow<List<Giphy>> {
        return repository.getGiphy()
    }
}