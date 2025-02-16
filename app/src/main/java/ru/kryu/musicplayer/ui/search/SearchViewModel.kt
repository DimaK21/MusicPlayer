package ru.kryu.musicplayer.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import ru.kryu.musicplayer.domain.DownloadRepository
import ru.kryu.musicplayer.domain.TrackNetworkRepository
import ru.kryu.musicplayer.domain.model.Resource
import ru.kryu.musicplayer.domain.model.Track
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val networkRepository: TrackNetworkRepository,
    private val downloadRepository: DownloadRepository,
) : ViewModel() {
    private val _state = MutableStateFlow<SearchState>(SearchState.Content(emptyList()))
    val state: StateFlow<SearchState> = _state.asStateFlow()

    init {
        getChart()
    }

    fun getChart() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = SearchState.Loading
            delay(500)

            networkRepository.getTopTracks()
                .catch { e ->
                    _state.value = SearchState.Error(e.localizedMessage ?: "")
                }
                .collect { resource ->
                    _state.value = when (resource) {
                        is Resource.Success -> SearchState.Content(resource.data ?: emptyList())
                        is Resource.Error -> SearchState.Error(resource.message ?: "")
                    }
                }
        }
    }

    fun search(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = SearchState.Loading
            delay(500)

            networkRepository.searchTracks(query)
                .catch { e ->
                    _state.value = SearchState.Error(e.localizedMessage ?: "")
                }
                .collect { resource ->
                    _state.value = when (resource) {
                        is Resource.Success -> SearchState.Content(resource.data ?: emptyList())
                        is Resource.Error -> SearchState.Error(resource.message ?: "Ошибка запроса")
                    }
                }
        }
    }

    fun downloadTrack(track: Track) {
        viewModelScope.launch(Dispatchers.IO) {
            downloadRepository.downloadTrack(track)
        }
    }
}