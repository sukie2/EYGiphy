package com.sukitha.ey.eygiphy.domain.usecase

import com.sukitha.ey.eygiphy.domain.repository.GiphyRemoteRepository

class GetTrendingGiphyUseCase(private val repository: GiphyRemoteRepository) {
    operator fun invoke() = repository.getTrendingGiphy()
}