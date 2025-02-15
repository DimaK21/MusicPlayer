package ru.kryu.musicplayer.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DownloadsDao {
    @Query("SELECT * FROM downloaded_tracks")
    fun getAllTracks(): Flow<List<DownloadedTrack>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrack(track: DownloadedTrack)

    @Delete
    suspend fun deleteTrack(track: DownloadedTrack)
}