package ru.kryu.musicplayer.ui.download

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.kryu.musicplayer.domain.model.Track
import javax.inject.Inject

@HiltViewModel
class DownloadsViewModel @Inject constructor(

) : ViewModel() {
    private val _state = MutableStateFlow<List<Track>>(emptyList())
    val state: StateFlow<List<Track>> = _state.asStateFlow()

}