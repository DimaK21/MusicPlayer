package ru.kryu.musicplayer.data.mapper

import ru.kryu.musicplayer.data.network.dto.ChartDto
import ru.kryu.musicplayer.data.network.dto.SearchTracksDto
import ru.kryu.musicplayer.domain.model.Track

fun ChartDto.toTrackList() = this.tracks?.data?.map {
    Track(
        id = it?.id ?: -1,
        title = it?.title ?: "",
        artist = it?.artist?.name ?: "",
        coverUrl = it?.album?.coverMedium,
        previewUrl = it?.preview,
        localPath = null
    )
} ?: emptyList<Track>()

fun SearchTracksDto.toTrackList() = this.data?.map {
    Track(
        id = it?.id ?: -1,
        title = it?.title ?: "",
        artist = it?.artist?.name ?: "",
        coverUrl = it?.album?.coverMedium,
        previewUrl = it?.preview,
        localPath = null
    )
} ?: emptyList<Track>()