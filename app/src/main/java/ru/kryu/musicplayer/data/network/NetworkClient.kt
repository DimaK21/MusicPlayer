package ru.kryu.musicplayer.data.network

import kotlinx.coroutines.flow.Flow
import ru.kryu.musicplayer.domain.model.Resource
import ru.kryu.musicplayer.domain.model.Track

interface NetworkClient {
    fun getChart(): Flow<Resource<List<Track>>>
    fun searchTracks(query: String): Flow<Resource<List<Track>>>
}