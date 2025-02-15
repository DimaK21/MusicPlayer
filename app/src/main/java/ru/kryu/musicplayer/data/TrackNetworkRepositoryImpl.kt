package ru.kryu.musicplayer.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.kryu.musicplayer.domain.TrackNetworkRepository
import ru.kryu.musicplayer.domain.model.Resource
import ru.kryu.musicplayer.domain.model.Track

class TrackNetworkRepositoryImpl : TrackNetworkRepository {
    override fun getTopTracks(): Flow<Resource<List<Track>>> = flow {
        emit(Resource.Success(emptyList()))
    }

    override fun searchTracks(query: String): Flow<Resource<List<Track>>> = flow {
        emit(Resource.Success(emptyList()))
    }
}