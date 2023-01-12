package com.sukitha.ey.eygiphy.domain.usecase

import com.sukitha.ey.eygiphy.domain.data.Giphy
import com.sukitha.ey.eygiphy.domain.repository.GiphyLocalRepository

class RemoveFavouriteGiphy(private val repository: GiphyLocalRepository) {
    suspend operator fun invoke(giphy: Giphy) {
        repository.deleteGiphy(giphy)
    }
}