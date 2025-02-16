package ru.kryu.musicplayer.ui.trackslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kryu.musicplayer.databinding.ItemTrackBinding
import ru.kryu.musicplayer.domain.model.Track

class TrackAdapter(
    private var tracks: List<Track> = emptyList(),
    private val onTrackClick: (Track) -> Unit,
    private val onIconClick: (Track) -> Unit,
) : RecyclerView.Adapter<TrackViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TrackViewHolder(
            ItemTrackBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(tracks[position], onTrackClick, onIconClick)
    }

    override fun getItemCount(): Int = tracks.size

    fun updateTracks(newTracks: List<Track>) {
        tracks = newTracks
        notifyDataSetChanged()
    }
}