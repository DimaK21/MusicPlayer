package ru.kryu.musicplayer.ui.download

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.kryu.musicplayer.databinding.FragmentDownloadsBinding
import ru.kryu.musicplayer.domain.model.Track
import ru.kryu.musicplayer.ui.trackslist.TrackAdapter

@AndroidEntryPoint
class DownloadsFragment : Fragment() {

    private var _binding: FragmentDownloadsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DownloadsViewModel by viewModels()
    private lateinit var adapter: TrackAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDownloadsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = TrackAdapter(
            onTrackClick = { track -> openPlayer(track) },
            onIconClick = { track -> deleteTrack(track) },
        )
        binding.rvTracks.adapter = adapter
        binding.rvTracks.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        lifecycleScope.launch {
            viewModel.state.collect { adapter.updateTracks(it) }
        }
    }

    private fun openPlayer(track: Track) {

    }

    private fun deleteTrack(track: Track) {
        viewModel.deleteTrack(track)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}