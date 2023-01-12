package com.sukitha.ey.eygiphy.domain.usecase

import com.sukitha.ey.eygiphy.domain.data.Giphy
import com.sukitha.ey.eygiphy.domain.repository.GiphyRepository

class InsertGiphyUseCase(private val repository: GiphyRepository) {
    suspend operator fun invoke(giphy: Giphy) {
        repository.insertGiphy(giphy)
    }
}