package ru.kryu.musicplayer.ui.download

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.kryu.musicplayer.domain.DeleteDownloadsRepository
import ru.kryu.musicplayer.domain.GetDownloadsRepository
import ru.kryu.musicplayer.domain.model.Track
import javax.inject.Inject

@HiltViewModel
class DownloadsViewModel @Inject constructor(
    private val getDownloadsRepository: GetDownloadsRepository,
    private val deleteDownloadsRepository: DeleteDownloadsRepository
) : ViewModel() {

    private val _state = MutableStateFlow<List<Track>>(emptyList())
    val state: StateFlow<List<Track>> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getDownloadsRepository.getDownloadedTracks().collect { list ->
                _state.update { list }
            }
        }
    }

    fun deleteTrack(track: Track) {
        viewModelScope.launch {
            deleteDownloadsRepository.deleteTrack(track)
        }
    }
}