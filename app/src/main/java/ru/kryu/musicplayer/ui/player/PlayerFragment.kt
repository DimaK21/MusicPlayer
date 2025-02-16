package ru.kryu.musicplayer.ui.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.kryu.musicplayer.R
import ru.kryu.musicplayer.databinding.FragmentPlayerBinding

@AndroidEntryPoint
class PlayerFragment : Fragment() {

    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PlayerViewModel by viewModels()
    private val args: PlayerFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val track = args.track
        viewModel.setTrack(track)

        lifecycleScope.launch {
            viewModel.currentTrack.collect { track ->
                track?.let {
                    binding.tvTrackTitle.text = it.title
                    binding.tvArtist.text = it.artist
                    Glide.with(requireContext())
                        .load(it.coverUrl ?: R.drawable.placeholder_album)
                        .centerCrop()
                        .placeholder(R.drawable.placeholder_album)
                        .into(binding.ivTrackCover)

                    viewModel.playTrack(
                        if (!it.localPath.isNullOrBlank()) {
                            it.localPath
                        } else {
                            it.previewUrl ?: ""
                        }
                    )
                }
            }
        }

        lifecycleScope.launch {
            viewModel.isPlaying.collect { isPlaying ->
                binding.btnPlayPause.setImageResource(
                    if (isPlaying) android.R.drawable.ic_media_pause
                    else android.R.drawable.ic_media_play
                )
            }
        }

        binding.btnPlayPause.setOnClickListener { viewModel.togglePlayPause() }

        lifecycleScope.launch {
            viewModel.currentPosition.collect { position ->
                binding.seekBar.progress = position
            }
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) viewModel.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.releasePlayer()
        _binding = null
    }
}