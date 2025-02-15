package ru.kryu.musicplayer.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.kryu.musicplayer.domain.TrackNetworkRepository
import ru.kryu.musicplayer.domain.model.Resource
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val networkRepository: TrackNetworkRepository
) : ViewModel() {
    private val _state = MutableStateFlow<SearchState>(SearchState.Content(emptyList()))
    val state: StateFlow<SearchState> = _state.asStateFlow()

    init {
        getChart()
    }

    fun getChart() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                SearchState.Loading
            }
            networkRepository.getTopTracks()
                .catch {
                    _state.update {
                        SearchState.Error
                    }
                }
                .collect { resource ->
                    if (resource is Resource.Success) {
                        _state.update {
                            SearchState.Content(resource.data ?: emptyList())
                        }
                    } else {
                        _state.update {
                            SearchState.Error
                        }
                    }
                }
        }
    }

    fun search(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                SearchState.Loading
            }
            networkRepository.searchTracks(query)
                .catch {
                    _state.update {
                        SearchState.Error
                    }
                }
                .collect { resource ->
                    if (resource is Resource.Success) {
                        _state.update {
                            SearchState.Content(resource.data ?: emptyList())
                        }
                    } else {
                        _state.update {
                            SearchState.Error
                        }
                    }
                }
        }
    }
}