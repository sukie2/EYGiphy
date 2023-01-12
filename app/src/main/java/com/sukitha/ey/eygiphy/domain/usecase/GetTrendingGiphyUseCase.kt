package com.sukitha.ey.eygiphy.domain.usecase

import com.sukitha.ey.eygiphy.domain.repository.GiphyRepository

class GetTrendingGiphyUseCase(private val repository: GiphyRepository) {
    operator fun invoke(offset: Int) = repository.getTrendingGiphy(offset)
}