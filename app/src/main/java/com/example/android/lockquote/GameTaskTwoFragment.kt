package com.example.android.lockquote

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class GameTaskTwoFragment : Fragment() {
    private lateinit var textBubble: TextView

    companion object {
        fun newInstance(): GameTaskTwoFragment {
            return GameTaskTwoFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game_task_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textBubble = view.findViewById(R.id.textBubble)
        // textBubble.text = lyric excerpt words
    }
}
