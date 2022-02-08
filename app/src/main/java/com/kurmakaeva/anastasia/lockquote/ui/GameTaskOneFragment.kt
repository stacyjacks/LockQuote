package com.kurmakaeva.anastasia.lockquote.ui

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kurmakaeva.anastasia.lockquote.R
import com.kurmakaeva.anastasia.lockquote.adapter.EditTextRecyclerViewAdapter
import com.kurmakaeva.anastasia.lockquote.adapter.RecyclerViewEditTextListener
import com.kurmakaeva.anastasia.lockquote.databinding.FragmentGameTaskOneBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameTaskOneFragment : Fragment(), RecyclerViewEditTextListener {

    private lateinit var binding: FragmentGameTaskOneBinding
    lateinit var adapter: EditTextRecyclerViewAdapter

    private val args by navArgs<GameTaskOneFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
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

        adapter = EditTextRecyclerViewAdapter(this, args.modPasswordString)
        binding.editTextRV.adapter = adapter
        binding.editTextRV.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)

        binding.clearPassButton.setOnClickListener {
            binding.editTextRV.adapter = null
            binding.editTextRV.adapter = adapter
            adapter.notifyDataSetChanged()
        }

        onContinueTapped()
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
            val action = GameTaskOneFragmentDirections
                .actionGameTaskOneFragmentToGameTaskTwoFragment(
                    args.modPasswordString, args.selectedLyric)
            this.findNavController().navigate(action)
        }
    }

    private fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager: InputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity.currentFocus?.windowToken, 0
        )
    }
}