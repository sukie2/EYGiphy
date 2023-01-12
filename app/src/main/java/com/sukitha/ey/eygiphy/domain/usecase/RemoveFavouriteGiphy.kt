package com.sukitha.ey.eygiphy.domain.usecase

import com.sukitha.ey.eygiphy.domain.data.Giphy
import com.sukitha.ey.eygiphy.domain.repository.GiphyRepository

class RemoveFavouriteGiphy(private val repository: GiphyRepository) {
    suspend operator fun invoke(giphy: Giphy) {
        repository.deleteGiphy(giphy)
    }
}