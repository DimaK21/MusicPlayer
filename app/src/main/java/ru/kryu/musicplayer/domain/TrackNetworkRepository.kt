package ru.kryu.musicplayer.domain

import kotlinx.coroutines.flow.Flow
import ru.kryu.musicplayer.domain.model.Resource
import ru.kryu.musicplayer.domain.model.Track

interface TrackNetworkRepository {
    fun getTopTracks(): Flow<Resource<List<Track>>>
    fun searchTracks(query: String): Flow<Resource<List<Track>>>
}