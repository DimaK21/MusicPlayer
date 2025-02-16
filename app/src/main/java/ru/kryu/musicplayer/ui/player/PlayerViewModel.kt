package ru.kryu.musicplayer.ui.player

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.kryu.musicplayer.domain.model.Track
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val musicPlayerManager: MusicPlayerManager
) : ViewModel() {
    private val _currentTrack = MutableStateFlow<Track?>(null)
    val currentTrack: StateFlow<Track?> = _currentTrack

    val isPlaying: StateFlow<Boolean> = musicPlayerManager.isPlaying
    val currentPosition: StateFlow<Int> = musicPlayerManager.currentPosition

    fun setTrack(track: Track) {
        _currentTrack.value = track
    }

    fun playTrack(url: String) {
        musicPlayerManager.playTrack(url)
    }

    fun togglePlayPause() {
        musicPlayerManager.togglePlayPause()
    }

    fun seekTo(position: Int) {
        musicPlayerManager.seekTo(position)
    }

    fun releasePlayer() {
        musicPlayerManager.release()
    }

    override fun onCleared() {
        super.onCleared()
        releasePlayer()
    }
}
