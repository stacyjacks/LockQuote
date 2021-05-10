package com.kurmakaeva.anastasia.lockquote.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kurmakaeva.anastasia.lockquote.R
import com.kurmakaeva.anastasia.lockquote.viewmodel.SearchViewModel.*

interface SongSearchAdapterListener {
    fun onShowDetails(position: Int)
}

class SongSearchAdapter(
    private val songListAdapterListener: SongSearchAdapterListener,
    private val context: Context): ListAdapter<SongSummaryViewData, SongSearchAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
            .from(parent.context)
            .inflate(R.layout.song_search_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val searchResultView = getItem(position)

        holder.songNameTextView.text = searchResultView?.title
        holder.artistNameTextView.text = searchResultView?.name

        Glide.with(context)
            .load(searchResultView?.header_image_thumbnail_url)
            .into(holder.songThumbnail)

        holder.itemView.setOnClickListener {
            songListAdapterListener.onShowDetails(position)
        }
    }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val songNameTextView: TextView = view.findViewById(R.id.songName)
            val artistNameTextView: TextView = view.findViewById(R.id.artistName)
            val songThumbnail: ImageView = view.findViewById(R.id.songThumbnail)
        }
}

class DiffCallback: DiffUtil.ItemCallback<SongSummaryViewData>() {
    override fun areItemsTheSame(oldItem: SongSummaryViewData, newItem: SongSummaryViewData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SongSummaryViewData, newItem: SongSummaryViewData): Boolean {
        return oldItem.id == newItem.id
    }
}
