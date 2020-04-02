package com.example.android.lockquote.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.lockquote.R

class TextBubbleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val textBubble = itemView.findViewById(R.id.textBubble) as TextView
}

interface OnStartDragListener {
    fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
}