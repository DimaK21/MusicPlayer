package ru.kryu.musicplayer.ui.search

import ru.kryu.musicplayer.domain.model.Track

sealed interface SearchState {
    object Loading : SearchState
    object Error : SearchState
    data class Content(val tracks: List<Track>) : SearchState
}