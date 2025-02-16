package ru.kryu.musicplayer.ui.download

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _allTracks = MutableStateFlow<List<Track>>(emptyList())
    private val _searchQuery = MutableStateFlow("")
    private val _filteredTracks = MutableStateFlow<List<Track>>(emptyList())
    val filteredTracks: StateFlow<List<Track>> = _filteredTracks.asStateFlow()

    init {
        viewModelScope.launch {
            getDownloadsRepository.getDownloadedTracks().collect { list ->
                _allTracks.value = list
                applyFilter()
            }
        }
    }

    fun search(query: String) {
        _searchQuery.value = query
        applyFilter()
    }

    fun deleteTrack(track: Track) {
        viewModelScope.launch {
            deleteDownloadsRepository.deleteTrack(track)
        }
    }

    private fun applyFilter() {
        _filteredTracks.value = _allTracks.value.filter {
            it.title.contains(_searchQuery.value, ignoreCase = true) ||
            it.artist.contains(_searchQuery.value, ignoreCase = true)
        }
    }
}