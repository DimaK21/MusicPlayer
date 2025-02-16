package ru.kryu.musicplayer.ui.trackslist

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.kryu.musicplayer.R
import ru.kryu.musicplayer.databinding.ItemTrackBinding
import ru.kryu.musicplayer.domain.model.Track

class TrackViewHolder(
    private val binding: ItemTrackBinding,
    private val iconResId: Int,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        track: Track,
        onClick: (Track) -> Unit,
        onIconClick: (Track) -> Unit,
    ) {
        binding.tvTrackTitle.text = track.title
        binding.tvArtist.text = track.artist

        Glide.with(itemView)
            .load(track.coverUrl ?: R.drawable.placeholder_album)
            .centerCrop()
            .placeholder(R.drawable.placeholder_album)
            .into(binding.ivTrackCover)

        binding.btnDownload.setImageResource(iconResId)
        itemView.setOnClickListener { onClick(track) }
        binding.btnDownload.setOnClickListener { onIconClick(track) }
    }
}