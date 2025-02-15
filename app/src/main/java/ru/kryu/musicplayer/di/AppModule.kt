package ru.kryu.musicplayer.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.kryu.musicplayer.data.TrackNetworkRepositoryImpl
import ru.kryu.musicplayer.data.network.DeezerApiService
import ru.kryu.musicplayer.data.network.NetworkClient
import ru.kryu.musicplayer.data.network.RetrofitNetworkClient
import ru.kryu.musicplayer.domain.TrackNetworkRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.deezer.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideDeezerApi(retrofit: Retrofit): DeezerApiService {
        return retrofit.create(DeezerApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkClient(deezerApiService: DeezerApiService): NetworkClient {
        return RetrofitNetworkClient(deezerApiService)
    }

    @Provides
    @Singleton
    fun provideTrackNetworkRepository(networkClient: NetworkClient): TrackNetworkRepository {
        return TrackNetworkRepositoryImpl(networkClient)
    }
}