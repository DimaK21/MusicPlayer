package ru.kryu.musicplayer.data

import kotlinx.coroutines.flow.Flow
import ru.kryu.musicplayer.data.network.NetworkClient
import ru.kryu.musicplayer.domain.TrackNetworkRepository
import ru.kryu.musicplayer.domain.model.Resource
import ru.kryu.musicplayer.domain.model.Track
import javax.inject.Inject

class TrackNetworkRepositoryImpl @Inject constructor(
    private val networkClient: NetworkClient
) : TrackNetworkRepository {
    override fun getTopTracks(): Flow<Resource<List<Track>>> {
        return networkClient.getChart()
    }

    override fun searchTracks(query: String): Flow<Resource<List<Track>>> {
        return networkClient.searchTracks(query)
    }
}