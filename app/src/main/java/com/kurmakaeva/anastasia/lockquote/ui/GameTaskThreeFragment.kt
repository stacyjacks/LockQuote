package com.kurmakaeva.anastasia.lockquote.ui

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.kurmakaeva.anastasia.lockquote.R
import com.kurmakaeva.anastasia.lockquote.adapter.*
import com.google.android.flexbox.FlexboxLayoutManager
import com.kurmakaeva.anastasia.lockquote.databinding.FragmentGameTaskThreeBinding

class GameTaskThreeFragment : Fragment(), OnCharBubbleStartDragListener, RecyclerViewCharBubbleListener {

    private lateinit var binding: FragmentGameTaskThreeBinding

    lateinit var adapter: CharBubbleRecyclerViewAdapter
    lateinit var touchHelper: ItemTouchHelper

    private val args by navArgs<GameTaskThreeFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_game_task_three,
            container,
            false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CharBubbleRecyclerViewAdapter(dividePasswordIntoCharArray(), this, this)
        binding.charBubbleRV.adapter = adapter
        binding.charBubbleRV.layoutManager = FlexboxLayoutManager(context)

        val callback: ItemTouchHelper.Callback = ItemMoveCharBubbleCallbackListener(adapter)

        touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.charBubbleRV)

        onContinueTapped()
    }

    private fun dividePasswordIntoCharArray(): Array<String> {
        return args.passwordString.split("").toTypedArray()
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        touchHelper.startDrag(viewHolder)
    }

    override fun onCorrectOrderCallback() {
        binding.successTaskThree.visibility = View.VISIBLE
        binding.charBubbleRV.addOnItemTouchListener(object: RecyclerView.SimpleOnItemTouchListener() {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                return true
            }
        })
    }

    private fun onContinueTapped() {
        binding.continueButtonTaskThree.setOnClickListener {
            // val action =
        }
    }
}