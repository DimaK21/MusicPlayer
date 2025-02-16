package ru.kryu.musicplayer.ui.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.kryu.musicplayer.domain.GetDownloadsRepository
import ru.kryu.musicplayer.domain.model.Track
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val musicPlayerManager: MusicPlayerManager,
    private val downloadsRepository: GetDownloadsRepository
) : ViewModel() {

    val currentTrack: StateFlow<Track?> = musicPlayerManager.currentTrack
    val isPlaying: StateFlow<Boolean> = musicPlayerManager.isPlaying
    val currentPosition: StateFlow<Int> = musicPlayerManager.currentPosition
    val trackDuration: StateFlow<Int> = musicPlayerManager.trackDuration

    init {
        viewModelScope.launch {
            downloadsRepository.getDownloadedTracks().collect{ tracks ->
                musicPlayerManager.setTrackList(tracks)
            }
        }
    }

    fun playTrack(track: Track) {
        musicPlayerManager.playTrack(track)
    }

    fun togglePlayPause() {
        musicPlayerManager.togglePlayPause()
    }

    fun seekTo(position: Int) {
        musicPlayerManager.seekTo(position)
    }

    fun playNextTrack() {
        musicPlayerManager.playNextTrack()
    }

    fun playPreviousTrack() {
        musicPlayerManager.playPreviousTrack()
    }

    fun releasePlayer() {
        musicPlayerManager.release()
    }

    override fun onCleared() {
        super.onCleared()
        releasePlayer()
    }
}
