package com.sukitha.ey.eygiphy.domain.usecase

import com.sukitha.ey.eygiphy.domain.repository.GiphyRepository

class GetGiphyUseCase(private val repository: GiphyRepository) {
    operator fun invoke(query: String, offset: Int) = repository.getGiphy(query, offset)
}