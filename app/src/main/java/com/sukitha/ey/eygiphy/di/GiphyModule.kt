package com.sukitha.ey.eygiphy.di

import com.sukitha.ey.eygiphy.domain.repository.GiphyRemoteRepository
import com.sukitha.ey.eygiphy.domain.usecase.GetGiphyUseCase
import com.sukitha.ey.eygiphy.domain.usecase.GetTrendingGiphyUseCase
import com.sukitha.ey.eygiphy.domain.usecase.GiphyUseCases
import com.sukitha.ey.eygiphy.data.remote.GiphyApi
import com.sukitha.ey.eygiphy.data.repository.GiphyRemoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GiphyModule {
    @Provides
    fun provideGiphyRemoteRepository(giphyApi: GiphyApi): GiphyRemoteRepository =
        GiphyRemoteRepositoryImpl(giphyApi)

    @Provides
    fun providesGiphyUseCase(giphyRemoteRepository: GiphyRemoteRepository): GiphyUseCases {
        return GiphyUseCases(
            getGiphyUseCase = GetGiphyUseCase(giphyRemoteRepository),
            getTrendingGiphyUseCase = GetTrendingGiphyUseCase(giphyRemoteRepository)
        )
    }
}