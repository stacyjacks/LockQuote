package com.kurmakaeva.anastasia.lockquote.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kurmakaeva.anastasia.lockquote.R
import com.kurmakaeva.anastasia.lockquote.adapter.EditTextRecyclerViewAdapter
import com.kurmakaeva.anastasia.lockquote.adapter.RecyclerViewEditTextListener
import com.kurmakaeva.anastasia.lockquote.databinding.FragmentGameTaskOneBinding

interface OnDataPass {
    fun onDataPass(data: String)
    fun passwordString(): String
    fun selectedLyric(): String
}

class GameTaskOneFragment : Fragment(), RecyclerViewEditTextListener {

    private lateinit var binding: FragmentGameTaskOneBinding
    lateinit var adapter: EditTextRecyclerViewAdapter
    lateinit var dataPass: OnDataPass

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_game_task_one,
            container,
            false
        )

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.helpfulTextTaskOne.text = getString(R.string.taskOneInputFullPassword)

        adapter = EditTextRecyclerViewAdapter(this, dataPass.passwordString())
        binding.editTextRV.adapter = adapter
        binding.editTextRV.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)

        binding.clearPassButton.setOnClickListener {
            binding.editTextRV.adapter = null
            binding.editTextRV.adapter = adapter
            adapter.notifyDataSetChanged()
        }

        onContinueTapped()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPass = context as OnDataPass
    }

    override fun onCorrectTextInputCallback() {
        hideSoftKeyboard(this.requireActivity())
        binding.apply {
            editTextRV.addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
                override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                    return true
                }
            })
            clearPassButton.visibility = View.INVISIBLE
            successTaskOne.visibility = View.VISIBLE
        }
    }

    private fun onContinueTapped() {
        binding.continueButtonTaskOne.setOnClickListener {

        }
    }

    private fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager: InputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity.currentFocus?.windowToken, 0
        )
    }

    fun passData(data: String){
        dataPass.onDataPass(data)
    }
}