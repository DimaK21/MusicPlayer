package ru.kryu.musicplayer.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Track(
    val id: Long,
    val title: String,
    val artist: String,
    val coverUrl: String?,
    val previewUrl: String?,
    val localPath: String?
) : Parcelable
