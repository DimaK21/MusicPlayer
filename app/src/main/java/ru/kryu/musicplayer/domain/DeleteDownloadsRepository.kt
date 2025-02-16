package ru.kryu.musicplayer.domain

import ru.kryu.musicplayer.domain.model.Track

interface DeleteDownloadsRepository {
    suspend fun deleteTrack(track: Track)
}