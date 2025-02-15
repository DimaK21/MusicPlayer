package ru.kryu.musicplayer.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "downloaded_tracks")
data class DownloadedTrack(
    @PrimaryKey val id: Long,
    val title: String,
    val artist: String,
    val coverUrl: String,
    val previewUrl: String,
    val localPath: String,
)
