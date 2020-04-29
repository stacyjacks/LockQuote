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
import com.example.android.lockquote.adapter.*
import com.google.android.flexbox.FlexboxLayoutManager

class GameTaskThreeFragment : Fragment(), OnDataPass, OnCharBubbleStartDragListener, RecyclerViewCharBubbleListener {
    lateinit var dataPass: OnDataPass
    lateinit var charBubbleRecyclerView: RecyclerView
    lateinit var adapter: CharBubbleRecyclerViewAdapter
    lateinit var touchHelper: ItemTouchHelper

    companion object {
        fun newInstance(): GameTaskThreeFragment {
            return GameTaskThreeFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game_task_three, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        charBubbleRecyclerView = view.findViewById(R.id.charBubbleRV)
        charBubbleRecyclerView.layoutManager = FlexboxLayoutManager(context)
        adapter = CharBubbleRecyclerViewAdapter(dividePasswordIntoCharArray(), this, this)
        charBubbleRecyclerView.adapter = adapter

        val callback: ItemTouchHelper.Callback = ItemMoveCharBubbleCallbackListener(adapter)

        touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(charBubbleRecyclerView)

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

    private fun dividePasswordIntoCharArray(): Array<String> {
        val passwordString = passwordString()

        return passwordString.split("").toTypedArray()
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        touchHelper.startDrag(viewHolder)
    }

    override fun onCorrectOrderCallback() {
        val checkAnimation = view?.findViewById<CheckView>(R.id.checkViewAnimation)
        checkAnimation?.visibility = CheckView.VISIBLE
        checkAnimation?.check()
        val continueButton = view?.findViewById<Button>(R.id.continueButtonTaskThree)
        continueButton?.visibility = View.VISIBLE
    }

    private fun onContinueTapped() {
        val continueButton = view?.findViewById<Button>(R.id.continueButtonTaskThree)
        continueButton?.setOnClickListener {
            val fragmentTaskFour = GameTaskFourFragment.newInstance()
            val transaction = fragmentManager?.beginTransaction()
            transaction
                ?.replace(R.id.frameFragmentGame, fragmentTaskFour)
                ?.addToBackStack(null)
                ?.commit()
        }
    }
}