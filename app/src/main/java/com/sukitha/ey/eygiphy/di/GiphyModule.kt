package com.sukitha.ey.eygiphy.di

import android.app.Application
import androidx.room.Room
import com.sukitha.ey.eygiphy.data.local.GiphyDatabase
import com.sukitha.ey.eygiphy.domain.repository.GiphyRemoteRepository
import com.sukitha.ey.eygiphy.data.remote.GiphyApi
import com.sukitha.ey.eygiphy.data.repository.GiphyLocalRepositoryImpl
import com.sukitha.ey.eygiphy.data.repository.GiphyRemoteRepositoryImpl
import com.sukitha.ey.eygiphy.domain.repository.GiphyLocalRepository
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
    fun provideGiphyRemoteRepository(giphyApi: GiphyApi): GiphyRemoteRepository =
        GiphyRemoteRepositoryImpl(giphyApi)

    @Provides
    fun providesGiphyUseCase(
        giphyRemoteRepository: GiphyRemoteRepository,
        giphyLocalRepository: GiphyLocalRepository
    ): GiphyUseCases {
        return GiphyUseCases(
            getGiphyUseCase = GetGiphyUseCase(giphyRemoteRepository),
            getTrendingGiphyUseCase = GetTrendingGiphyUseCase(giphyRemoteRepository),
            insertGiphy = InsertGiphyUseCase(giphyLocalRepository),
            getFavouriteGiphy = GetFavouriteGiphyUseCase(giphyLocalRepository),
            removeFavouriteGiphy = RemoveFavouriteGiphy(giphyLocalRepository)
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

    @Provides
    @Singleton
    fun provideGiphyRepository(db: GiphyDatabase): GiphyLocalRepository {
        return GiphyLocalRepositoryImpl(db.giphyDao)
    }
}