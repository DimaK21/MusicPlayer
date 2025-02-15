package ru.kryu.musicplayer.ui.search

import ru.kryu.musicplayer.domain.model.Track

sealed interface SearchState {
    object Loading : SearchState
    data class Error(val message: String) : SearchState
    data class Content(val tracks: List<Track>) : SearchState
}