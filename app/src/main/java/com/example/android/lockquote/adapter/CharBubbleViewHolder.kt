package com.example.android.lockquote.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.lockquote.R

class CharBubbleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val charBubble = itemView.findViewById(R.id.charBubble) as TextView
}

interface OnCharBubbleStartDragListener {
    fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
}