package com.example.android.lockquote

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import cdflynn.android.library.checkview.CheckView
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import com.example.android.lockquote.adapter.*

class GameTaskTwoFragment : Fragment(), OnDataPass, OnTextBubbleStartDragListener, RecyclerViewTextBubbleListener {
    lateinit var dataPass: OnDataPass
    lateinit var textBubbleRecyclerView: RecyclerView
    lateinit var adapter: TextBubbleRecyclerViewAdapter
    lateinit var touchHelper: ItemTouchHelper

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
        textBubbleRecyclerView = view.findViewById(R.id.textBubbleRV)
        val chipsLayoutManager: ChipsLayoutManager = ChipsLayoutManager.newBuilder(this.context)
                .setChildGravity(Gravity.TOP)
                .setScrollingEnabled(true)
                .setGravityResolver { Gravity.CENTER }
                .build()
        textBubbleRecyclerView.layoutManager = chipsLayoutManager
        adapter = TextBubbleRecyclerViewAdapter(divideLyricIntoWordsArray(), this, this)
        textBubbleRecyclerView.adapter = adapter

        val callback: ItemTouchHelper.Callback = ItemMoveTextBubbleCallbackListener(adapter)

        touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(textBubbleRecyclerView)

        onContinueTapped()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPass = context as OnDataPass
    }

    override fun onDataPass(data: String) {
    }

    override fun passwordString(): String {
        return dataPass.passwordString()
    }

    override fun selectedLyric(): String {
        return dataPass.selectedLyric()
    }

    fun passData(data: String) {
        dataPass.onDataPass(data)
    }

    private fun divideLyricIntoWordsArray(): Array<String> {
        val selectedLyric = selectedLyric()
        val regex = Regex("(\\s|\\\\n)")
        val lyricWordsArray = selectedLyric.split(regex).toTypedArray()

        return lyricWordsArray.map { it.replace(",", "") }.toTypedArray()
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        touchHelper.startDrag(viewHolder)
    }

    override fun onCorrectOrderCallback() {
        val checkAnimation = view?.findViewById<CheckView>(R.id.checkViewAnimation)
        checkAnimation?.visibility = CheckView.VISIBLE
        checkAnimation?.check()
        val continueButton = view?.findViewById<Button>(R.id.continueButtonTaskOne)
        continueButton?.visibility = View.VISIBLE
    }

    private fun onContinueTapped() {
        val continueButton = view?.findViewById<Button>(R.id.continueButtonTaskOne)
        continueButton?.setOnClickListener {
            val fragmentTaskThree = GameTaskThreeFragment.newInstance()
            val transaction = fragmentManager?.beginTransaction()
            transaction
                ?.replace(R.id.frameFragmentGame, fragmentTaskThree)
                ?.addToBackStack(null)
                ?.commit()
        }
    }
}
