package ru.kryu.musicplayer.domain.model

data class Track(
    val id: Long,
    val title: String,
    val artist: String,
    val coverUrl: String?,
    val previewUrl: String?,
    val localPath: String?
)
