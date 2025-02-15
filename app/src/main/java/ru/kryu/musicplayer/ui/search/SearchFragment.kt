package ru.kryu.musicplayer.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.kryu.musicplayer.databinding.FragmentSearchBinding
import ru.kryu.musicplayer.domain.model.Track
import ru.kryu.musicplayer.ui.trackslist.TrackAdapter

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var adapter: TrackAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentSearchBinding.bind(view)
        adapter = TrackAdapter { track -> openPlayer(track) }
        binding.rvApiTracks.adapter = adapter

        binding.searchButton.setOnClickListener {
            val query = binding.etSearch.text.toString()
            if (query.isNotBlank()) viewModel.search(query)
        }

        lifecycleScope.launch {
            viewModel.state.collect { adapter.updateTracks(it) }
        }
    }

    private fun openPlayer(track: Track) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}