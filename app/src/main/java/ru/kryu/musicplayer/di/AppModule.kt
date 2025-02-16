package ru.kryu.musicplayer.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.kryu.musicplayer.data.DeleteDownloadsRepositoryImpl
import ru.kryu.musicplayer.data.DownloadRepositoryImpl
import ru.kryu.musicplayer.data.GetDownloadsRepositoryImpl
import ru.kryu.musicplayer.data.TrackNetworkRepositoryImpl
import ru.kryu.musicplayer.data.local.AppDatabase
import ru.kryu.musicplayer.data.local.DownloadsDao
import ru.kryu.musicplayer.data.network.DeezerApiService
import ru.kryu.musicplayer.data.network.NetworkClient
import ru.kryu.musicplayer.data.network.RetrofitNetworkClient
import ru.kryu.musicplayer.domain.DeleteDownloadsRepository
import ru.kryu.musicplayer.domain.DownloadRepository
import ru.kryu.musicplayer.domain.GetDownloadsRepository
import ru.kryu.musicplayer.domain.TrackNetworkRepository
import ru.kryu.musicplayer.ui.player.MusicPlayerManager
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

    @Provides
    @Singleton
    fun provideDownloadRepository(
        @ApplicationContext context: Context,
        downloadsDao: DownloadsDao,
    ): DownloadRepository {
        return DownloadRepositoryImpl(context, downloadsDao)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "tracks.db").build()
    }

    @Provides
    @Singleton
    fun provideDownloadsDao(db: AppDatabase): DownloadsDao {
        return db.downloadsDao()
    }

    @Provides
    @Singleton
    fun provideDeleteDownloadsRepository(
        downloadsDao: DownloadsDao,
    ): DeleteDownloadsRepository {
        return DeleteDownloadsRepositoryImpl(downloadsDao)
    }

    @Provides
    @Singleton
    fun provideGetDownloadsRepository(
        downloadsDao: DownloadsDao,
    ): GetDownloadsRepository {
        return GetDownloadsRepositoryImpl(downloadsDao)
    }

    @Provides
    @Singleton
    fun provideMusicPlayerManager(): MusicPlayerManager {
        return MusicPlayerManager()
    }
}