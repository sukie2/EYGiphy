package com.sukitha.ey.eygiphy.domain.usecase

import com.sukitha.ey.eygiphy.domain.repository.GiphyRemoteRepository

class GetGiphyUseCase(private val repository: GiphyRemoteRepository) {
    operator fun invoke(query: String) = repository.getGiphy(query)
}