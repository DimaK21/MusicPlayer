package ru.kryu.musicplayer.data

import android.content.Context
import android.os.Environment
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kryu.musicplayer.data.local.DownloadedTrack
import ru.kryu.musicplayer.data.local.DownloadsDao
import ru.kryu.musicplayer.domain.DownloadRepository
import ru.kryu.musicplayer.domain.model.Track
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

class DownloadRepositoryImpl @Inject constructor(
    private val context: Context,
    private val downloadsDao: DownloadsDao
) : DownloadRepository {

    override suspend fun downloadTrack(track: Track) {
        val fileName = "${track.id}.mp3"
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_MUSIC), fileName)
        if (file.exists()) return

        withContext(Dispatchers.IO) {
            try {
                val url = URL(track.previewUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.connect()

                val inputStream = connection.inputStream
                val outputStream = FileOutputStream(file)
                val buffer = ByteArray(1024)
                var length: Int

                while (inputStream.read(buffer).also { length = it } != -1) {
                    outputStream.write(buffer, 0, length)
                }

                outputStream.flush()
                outputStream.close()
                inputStream.close()
                connection.disconnect()

                val downloadedTrack = DownloadedTrack(
                    track.id,
                    track.title,
                    track.artist,
                    track.coverUrl ?: "",
                    track.previewUrl ?: "",
                    file.path
                )
                downloadsDao.insertTrack(downloadedTrack)
            } catch (e: Exception) {
                Log.e(this.javaClass.simpleName, e.message.toString())
                Log.e(this.javaClass.simpleName, e.stackTraceToString())
            }
        }
    }
}