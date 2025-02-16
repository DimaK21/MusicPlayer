package ru.kryu.musicplayer.domain

import kotlinx.coroutines.flow.Flow
import ru.kryu.musicplayer.domain.model.Track

interface GetDownloadsRepository {
    fun getDownloadedTracks(): Flow<List<Track>>
}