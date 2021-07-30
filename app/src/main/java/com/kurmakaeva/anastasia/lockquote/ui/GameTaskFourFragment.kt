package com.kurmakaeva.anastasia.lockquote.ui

import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.airbnb.lottie.LottieAnimationView
import com.kurmakaeva.anastasia.lockquote.R
import com.kurmakaeva.anastasia.lockquote.databinding.FragmentGameTaskFourBinding

class GameTaskFourFragment : Fragment() {

    private lateinit var binding: FragmentGameTaskFourBinding
    private val args by navArgs<GameTaskFourFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_game_task_four,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.radioButton2.text = args.passwordString
        val shuffledPasswordString = args.passwordString
            .toCharArray()
            .toMutableList()
            .shuffled()
            .toString()
            .replace("[\\[\\]]", "")
            .replace(",", "")
            .replace("]", "")
            .replace("[", "")
            .replace(" ", "")
        binding.apply {
            radioButton1.text = shuffledPasswordString
            radioButton3.text = args.passwordString.reversed()
            radioButton4.text = shuffledPasswordString.reversed().lowercase()
        }

        onRadioButtonClicked()
        onContinueTapped()
    }

    private fun onRadioButtonClicked() {
        binding.multipleChoiceTaskFour.setOnCheckedChangeListener { radioGroup, i ->
            when {
                binding.radioButton2.isChecked -> {
                    binding.radioButton2.background = ContextCompat.getDrawable(binding.radioButton2.context, R.drawable.bubble)

                    val blinkingAnimation: Animation = AlphaAnimation(0.0f, 1.0f)
                    blinkingAnimation.duration = 100
                    blinkingAnimation.startOffset = 20
                    blinkingAnimation.repeatMode = Animation.REVERSE
                    blinkingAnimation.repeatCount = 5
                    binding.radioButton2.startAnimation(blinkingAnimation)
                }
                binding.radioButton1.isChecked -> {
                    binding.radioButton1.background = ContextCompat.getDrawable(binding.radioButton1.context,
                        R.drawable.bubble_red
                    )
                    binding.radioButton1.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
                }
                binding.radioButton3.isChecked -> {
                    binding.radioButton3.background = ContextCompat.getDrawable(binding.radioButton3.context,
                        R.drawable.bubble_red
                    )
                    binding.radioButton3.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
                }
                binding.radioButton4.isChecked -> {
                    binding.radioButton4.background = ContextCompat.getDrawable(binding.radioButton4.context,
                        R.drawable.bubble_red
                    )
                    binding.radioButton4.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
                }
            }
            if (binding.radioButton2.isChecked) {
                onCorrectChoice()
            }
        }
    }

    private fun onCorrectChoice() {
        binding.checkViewAnimation.visibility = LottieAnimationView.VISIBLE
        binding.successTaskFour.visibility = View.VISIBLE
    }

    private fun onContinueTapped() {
        binding.continueButtonTaskFour.setOnClickListener {
            val action = GameTaskFourFragmentDirections
                .actionGameTaskFourFragmentToGameTaskFiveFragment(args.passwordString, args.selectedLyric)
            this.findNavController().navigate(action)
        }
    }
}