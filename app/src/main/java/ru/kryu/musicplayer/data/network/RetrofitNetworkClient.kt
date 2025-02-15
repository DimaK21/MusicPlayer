package ru.kryu.musicplayer.data.network

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.kryu.musicplayer.data.mapper.toTrackList
import ru.kryu.musicplayer.domain.model.Resource
import ru.kryu.musicplayer.domain.model.Track
import javax.inject.Inject

class RetrofitNetworkClient @Inject constructor(
    private val deezerApiService: DeezerApiService
) : NetworkClient {

    override fun getChart(): Flow<Resource<List<Track>>> = flow {
        try {
            val response = deezerApiService.getTopTracks()
            if (response.isSuccessful) {
                emit(Resource.Success(data = response.body()?.toTrackList() ?: emptyList()))
            } else {
                emit(Resource.Error(message = response.errorBody().toString()))
            }
        } catch (e: Exception) {
            Log.e(this.javaClass.simpleName, e.message.toString())
            Log.e(this.javaClass.simpleName, e.stackTraceToString())
            emit(Resource.Error(message = e.message.toString()))
        }
    }

    override fun searchTracks(query: String): Flow<Resource<List<Track>>> = flow {
        try {
            val response = deezerApiService.searchTracks(query)
            if (response.isSuccessful) {
                emit(Resource.Success(data = response.body()?.toTrackList() ?: emptyList()))
            } else {
                emit(Resource.Error(message = response.errorBody().toString()))
            }
        } catch (e: Exception) {
            Log.e(this.javaClass.simpleName, e.message.toString())
            Log.e(this.javaClass.simpleName, e.stackTraceToString())
            emit(Resource.Error(message = e.message.toString()))
        }
    }

}