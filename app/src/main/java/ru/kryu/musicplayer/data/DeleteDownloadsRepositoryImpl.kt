package ru.kryu.musicplayer.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kryu.musicplayer.data.local.DownloadsDao
import ru.kryu.musicplayer.data.mapper.toDownloadedTrack
import ru.kryu.musicplayer.domain.DeleteDownloadsRepository
import ru.kryu.musicplayer.domain.model.Track
import java.io.File
import javax.inject.Inject

class DeleteDownloadsRepositoryImpl @Inject constructor(
    private val downloadsDao: DownloadsDao
) : DeleteDownloadsRepository {
    override suspend fun deleteTrack(track: Track) {
        val downloadedTrack = track.toDownloadedTrack()
        withContext(Dispatchers.IO) {
            val file = File(downloadedTrack.localPath)
            if (file.exists()) file.delete()
            downloadsDao.deleteTrack(downloadedTrack)
        }
    }
}