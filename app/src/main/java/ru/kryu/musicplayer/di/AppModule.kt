package ru.kryu.musicplayer.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kryu.musicplayer.data.TrackNetworkRepositoryImpl
import ru.kryu.musicplayer.domain.TrackNetworkRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTrackNetworkRepository(): TrackNetworkRepository {
        return TrackNetworkRepositoryImpl()
    }
}