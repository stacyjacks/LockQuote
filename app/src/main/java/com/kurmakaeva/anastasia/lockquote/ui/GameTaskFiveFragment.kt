package com.kurmakaeva.anastasia.lockquote.ui

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.airbnb.lottie.LottieAnimationView
import com.kurmakaeva.anastasia.lockquote.R
import com.google.android.material.textfield.TextInputEditText
import com.kurmakaeva.anastasia.lockquote.databinding.FragmentGameTaskFiveBinding
import com.kurmakaeva.anastasia.lockquote.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameTaskFiveFragment: Fragment() {

    private lateinit var binding: FragmentGameTaskFiveBinding
    private val args by navArgs<GameTaskFiveFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_game_task_five,
            container,
            false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editTextFields: Array<TextInputEditText> = arrayOf(
            binding.editTextField1,
            binding.editTextField2,
            binding.editTextField3,
            binding.editTextField4,
            binding.editTextField5,
            binding.editTextField6,
            binding.editTextField7,
            binding.editTextField8,
            binding.editTextField9,
            binding.editTextField10,
            binding.editTextField11,
            binding.editTextField12,
            binding.editTextField13,
            binding.editTextField14,
            binding.editTextField15
        )

        val passwordCharCount = args.passwordString.count()
        editTextDisplayFirst(editTextFields, passwordCharCount)

        for (editTextField in editTextFields) {
                editTextField.addTextChangedListener(object : TextWatcher {
                    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                        val arrayPosition = editTextFields.indexOf(editTextField)
                        onTextInput(editTextFields, arrayPosition)
                        if (s.isBlank()) {
                            editTextField.background = ContextCompat.getDrawable(editTextField.context,
                                R.drawable.edit_text_style
                            )
                        }
                        onCorrectTextInputCallback(editTextFields, editTextFields.map {
                            it.text
                        }.joinToString(""))
                    }

                    override fun afterTextChanged(s: Editable) { }

                    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }
                })
        }
        onContinueTapped()
        onStartOverTapped()
    }

    private fun editTextDisplayFirst(editTextFields: Array<TextInputEditText>, number: Int)  {
        if (number-1 > editTextFields.size) { return }

        for (i in 0 until number) {
            editTextFields[i].visibility = View.VISIBLE
        }
    }

    private fun onTextInput(editTextFields: Array<TextInputEditText>, arrayPosition: Int) {
        val passwordStringCharArray = args.passwordString.toCharArray()

        if (!passwordStringCharArray.indices.contains(arrayPosition)) return
        if (!editTextFields.indices.contains(arrayPosition)) return

        if (editTextFields[arrayPosition].text.toString() == (passwordStringCharArray[arrayPosition].toString())) {
            editTextFields[arrayPosition].background = ContextCompat.getDrawable(editTextFields[arrayPosition].context, R.drawable.bubble)

            editTextFields[arrayPosition].focusSearch(View.FOCUS_RIGHT)?.requestFocus()
        } else {
            editTextFields[arrayPosition].requestFocus()
            editTextFields[arrayPosition].performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
            editTextFields[arrayPosition].text?.clear()
        }
    }

    private fun onCorrectTextInputCallback(editTextFields: Array<TextInputEditText>, computedPassword: String) {
        if (args.passwordString == computedPassword) {
            hideKeyboard(requireActivity())
            for (editTextField in editTextFields) {
                editTextField.clearFocus()
                editTextField.setOnTouchListener { view, motionEvent ->
                    if (motionEvent.action == MotionEvent.ACTION_BUTTON_PRESS) {
                        editTextField.isEnabled = false
                    }
                    true
                }
            }

            binding.apply {
                helpButtons.visibility = View.GONE
                checkViewAnimation.visibility = LottieAnimationView.VISIBLE
                successTaskFive.visibility = View.VISIBLE
            }
        }
    }

    private fun onContinueTapped() {
        binding.continueButtonTaskFive.setOnClickListener {
            val action = GameTaskFiveFragmentDirections
                .actionGameTaskFiveFragmentToGameResultFragment(args.passwordString, args.selectedLyric)
            this.findNavController().navigate(action)
        }
    }

    private fun onStartOverTapped() {
        binding.takeMeBack.setOnClickListener {
            activity?.finish()
        }
    }
}