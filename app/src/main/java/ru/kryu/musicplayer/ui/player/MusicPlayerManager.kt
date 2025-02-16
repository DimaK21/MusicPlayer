package ru.kryu.musicplayer.ui.player

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.kryu.musicplayer.domain.model.Track
import javax.inject.Inject

class MusicPlayerManager @Inject constructor() {
    private var mediaPlayer: MediaPlayer? = null
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying
    private val _currentPosition = MutableStateFlow(0)
    val currentPosition: StateFlow<Int> = _currentPosition
    private val _trackDuration = MutableStateFlow(0)
    val trackDuration: StateFlow<Int> = _trackDuration
    private val _currentTrack = MutableStateFlow<Track?>(null)
    val currentTrack: StateFlow<Track?> = _currentTrack

    private var trackList: List<Track> = emptyList()

    fun setTrackList(tracks: List<Track>) {
        trackList = tracks
    }

    fun playTrack(track: Track) {
        _currentTrack.value = track
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setDataSource(track.localPath ?: track.previewUrl ?: "")
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            prepareAsync()
            setOnPreparedListener {
                start()
                _isPlaying.value = true
                _trackDuration.value = duration
                updateSeekBar()
            }
            setOnCompletionListener {
                _isPlaying.value = false
                playNextTrack()
            }
        }
    }

    fun togglePlayPause() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
                _isPlaying.value = false
            } else {
                it.start()
                _isPlaying.value = true
                updateSeekBar()
            }
        }
    }

    fun seekTo(position: Int) {
        mediaPlayer?.seekTo(position)
    }

    private fun updateSeekBar() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                _currentPosition.value = it.currentPosition
                Handler(Looper.getMainLooper()).postDelayed({ updateSeekBar() }, 1000)
            }
        }
    }

    fun playNextTrack() {
        val nextTrack = getNextTrack()
        nextTrack?.let { playTrack(it) }
    }

    fun playPreviousTrack() {
        val prevTrack = getPreviousTrack()
        prevTrack?.let { playTrack(it) }
    }

    private fun getNextTrack(): Track? {
        val index = trackList.indexOf(_currentTrack.value)
        return if (index != -1 && index < trackList.size - 1) {
            trackList[index + 1]
        } else {
            trackList.firstOrNull()
        }
    }

    private fun getPreviousTrack(): Track? {
        val index = trackList.indexOf(_currentTrack.value)
        return if (index > 0) {
            trackList[index - 1]
        } else {
            trackList.lastOrNull()
        }
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}