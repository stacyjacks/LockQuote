package com.kurmakaeva.anastasia.lockquote.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.kurmakaeva.anastasia.lockquote.R
import com.kurmakaeva.anastasia.lockquote.adapter.*
import com.google.android.flexbox.FlexboxLayoutManager
import com.kurmakaeva.anastasia.lockquote.databinding.FragmentGameTaskTwoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameTaskTwoFragment : Fragment(), OnTextBubbleStartDragListener,
    RecyclerViewTextBubbleListener {

    private lateinit var binding: FragmentGameTaskTwoBinding
    lateinit var adapter: TextBubbleRecyclerViewAdapter
    lateinit var touchHelper: ItemTouchHelper

    private val args by navArgs<GameTaskTwoFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_game_task_two,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = TextBubbleRecyclerViewAdapter(divideLyricIntoWordsArray(), this, this)
        binding.apply {
            textBubbleRV.adapter = adapter
            textBubbleRV.layoutManager = FlexboxLayoutManager(context)
        }

        val callback: ItemTouchHelper.Callback = ItemMoveTextBubbleCallbackListener(adapter)

        touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.textBubbleRV)

        onContinueTapped()
    }

    private fun divideLyricIntoWordsArray(): Array<String> {
        val selectedLyric = args.selectedLyric
        val regex = Regex("(\\s|\\\\n)")
        val lyricWordsArray = selectedLyric.split(regex).toTypedArray()

        return lyricWordsArray.map { it.replace(",", "") }.toTypedArray()
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        touchHelper.startDrag(viewHolder)
    }

    override fun onCorrectOrderCallback() {
        binding.apply {
            successTaskTwo.visibility = View.VISIBLE
            textBubbleRV.addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
                override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                    return true
                }
            })
        }
    }

    private fun onContinueTapped() {
        binding.continueButtonTaskTwo.setOnClickListener {
            val action = GameTaskTwoFragmentDirections.actionGameTaskTwoFragmentToGameTaskThreeFragment(
                args.passwordString, args.selectedLyric)

            this.findNavController().navigate(action)
        }
    }
}
