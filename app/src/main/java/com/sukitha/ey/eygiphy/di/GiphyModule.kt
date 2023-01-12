package com.sukitha.ey.eygiphy.di

import android.app.Application
import androidx.room.Room
import com.sukitha.ey.eygiphy.data.local.GiphyDatabase
import com.sukitha.ey.eygiphy.data.remote.GiphyApi
import com.sukitha.ey.eygiphy.data.repository.GiphyRepositoryImpl
import com.sukitha.ey.eygiphy.domain.repository.GiphyRepository
import com.sukitha.ey.eygiphy.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GiphyModule {
    @Provides
    fun provideGiphyRemoteRepository(giphyApi: GiphyApi, db: GiphyDatabase): GiphyRepository =
        GiphyRepositoryImpl(giphyApi, db.giphyDao)

    @Provides
    fun providesGiphyUseCase(
        giphyRepository: GiphyRepository
    ): GiphyUseCases {
        return GiphyUseCases(
            getGiphyUseCase = GetGiphyUseCase(giphyRepository),
            getTrendingGiphyUseCase = GetTrendingGiphyUseCase(giphyRepository),
            insertGiphy = InsertGiphyUseCase(giphyRepository),
            getFavouriteGiphy = GetFavouriteGiphyUseCase(giphyRepository),
            removeFavouriteGiphy = RemoveFavouriteGiphy(giphyRepository)
        )
    }

    @Provides
    @Singleton
    fun provideGiphyDatabaseDatabase(app: Application): GiphyDatabase {
        return Room.databaseBuilder(
            app,
            GiphyDatabase::class.java,
            GiphyDatabase.DATABASE_NAME
        ).build()
    }
}