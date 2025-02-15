package ru.kryu.musicplayer.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.kryu.musicplayer.data.network.dto.ChartDto
import ru.kryu.musicplayer.data.network.dto.SearchTracksDto

interface DeezerApiService {
    @GET("chart")
    suspend fun getTopTracks(): Response<ChartDto>

    @GET("search")
    suspend fun searchTracks(@Query("q") query: String): Response<SearchTracksDto>
}