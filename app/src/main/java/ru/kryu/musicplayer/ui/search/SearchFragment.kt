package ru.kryu.musicplayer.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = TrackAdapter(
            onTrackClick = { track -> openPlayer(track) },
            onDownloadClick = { track -> downloadTrack(track) },
        )
        binding.rvApiTracks.adapter = adapter
        binding.rvApiTracks.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        binding.etSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val query = binding.etSearch.text.toString()
                if (query.isNotBlank()) viewModel.search(query)
                true
            }
            false
        }

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is SearchState.Content -> showContent(state.tracks)
                    SearchState.Error -> showError()
                    SearchState.Loading -> showLoading()
                }
            }
        }
    }

    private fun showContent(tracks: List<Track>) {
        adapter.updateTracks(tracks)
    }

    private fun showError() {

    }

    private fun showLoading() {

    }

    private fun openPlayer(track: Track) {

    }

    private fun downloadTrack(track: Track) {
        viewModel.downloadTrack(track)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}