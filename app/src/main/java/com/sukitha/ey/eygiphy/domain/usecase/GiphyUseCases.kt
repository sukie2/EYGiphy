package com.sukitha.ey.eygiphy.domain.usecase

data class GiphyUseCases(
    val insertGiphy: InsertGiphyUseCase,
    val getFavouriteGiphy: GetFavouriteGiphyUseCase,
    val getGiphyUseCase: GetGiphyUseCase,
    val getTrendingGiphyUseCase: GetTrendingGiphyUseCase,
    val removeFavouriteGiphy: RemoveFavouriteGiphy
)
