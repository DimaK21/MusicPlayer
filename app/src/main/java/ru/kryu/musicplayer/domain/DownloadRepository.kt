package ru.kryu.musicplayer.domain

import ru.kryu.musicplayer.domain.model.Track

interface DownloadRepository {
    suspend fun downloadTrack(track: Track)
}