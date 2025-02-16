package ru.kryu.musicplayer.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.kryu.musicplayer.data.local.DownloadsDao
import ru.kryu.musicplayer.data.mapper.toTrack
import ru.kryu.musicplayer.domain.GetDownloadsRepository
import ru.kryu.musicplayer.domain.model.Track
import javax.inject.Inject

class GetDownloadsRepositoryImpl @Inject constructor(
    private val downloadsDao: DownloadsDao
) : GetDownloadsRepository {
    override fun getDownloadedTracks(): Flow<List<Track>> =
        downloadsDao.getAllTracks().map { it.map { downloadedTrack -> downloadedTrack.toTrack() } }
}