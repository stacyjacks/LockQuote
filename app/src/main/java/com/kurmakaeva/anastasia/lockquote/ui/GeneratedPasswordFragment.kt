package com.kurmakaeva.anastasia.lockquote.ui

import android.animation.Animator
import android.os.Bundle
import android.text.SpannedString
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kurmakaeva.anastasia.lockquote.R
import com.kurmakaeva.anastasia.lockquote.databinding.FragmentGeneratedPasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GeneratedPasswordFragment() : Fragment() {

    private lateinit var binding: FragmentGeneratedPasswordBinding

    private val viewModel: GeneratedPasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_generated_password, container, false)
        
        binding.loadingPass.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {}

            override fun onAnimationEnd(animation: Animator?) {
                binding.apply {
                    loadingPass.visibility = View.GONE
                    justASec.visibility = View.GONE

                    mainContentLinearLayout.visibility = View.VISIBLE
                    bottomViewGroup.visibility = View.VISIBLE
                }
            }

            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args by navArgs<GeneratedPasswordFragmentArgs>()

        viewModel.getPasswordStringFromSelectedText(args.passwordString)
        viewModel.passwordString.observe(viewLifecycleOwner, Observer {
            val modPasswordString = charReplacer(it)
            binding.generatedPass.text = modPasswordString

            binding.numberOfCharacters.text = numberOfCharCalculator(it)

            binding.helpMeRemember.setOnClickListener {
                val action = GeneratedPasswordFragmentDirections
                    .actionGeneratedPasswordFragmentToActivityGame(modPasswordString, args.selectedText)
                this.findNavController().navigate(action)
            }
        })

        binding.tryAgainButton.setOnClickListener {
            this.findNavController().navigateUp()
        }

        binding.selectedTextFromLyric.text = makeFirstLetterBold(args.selectedText)
    }

    private fun numberOfCharCalculator(passwordString: String): String {
        return passwordString.length.toString()
    }

    private fun makeFirstLetterBold(selectedLyric: String): SpannedString {
        var customString = SpannedString("")
        val regex = Regex("(\\s|\\\\n)")
        val lyricWordsArray = selectedLyric.split(regex).toTypedArray()
        for (word in lyricWordsArray) {
            if (word.isBlank()) {
                continue
            }

            val firstLetterBold = word.first()
            val normalText = word.drop(1)
            val customWord = SpannedString(buildSpannedString {
                bold {
                    append(firstLetterBold)
                }
                append(normalText)
            })
            customString = TextUtils.concat(customString, " ", customWord) as SpannedString
        }
        return customString.drop(1) as SpannedString
    }

    private fun charReplacer(passwordString: String): String {
        val replaceA = arrayListOf("A", "a", "4")
        val replaceS = arrayListOf("S", "s", "5")
        val replaceE = arrayListOf("E", "e", "3")
        val replaceT = arrayListOf("T", "t", "7")
        val replaceI = arrayListOf("I", "i", "1")
        val replaceO = arrayListOf("O", "o", "0")
        val replaceG = arrayListOf("G", "g", "6")

        return passwordString
            .replace("A", replaceA.random())
            .replace("??", replaceA.random())
            .replace("??", replaceA.random())
            .replace("??", replaceA.random())
            .replace("??", replaceA.random())
            .replace("??", replaceA.random())
            .replace("??", replaceA.random())
            .replace("??", replaceA.random())
            .replace("??", replaceA.random())
            .replace("a", replaceA.random())
            .replace("S", replaceS.random())
            .replace("s", replaceS.random())
            .replace("E", replaceE.random())
            .replace("??", replaceE.random())
            .replace("??", replaceE.random())
            .replace("??", replaceE.random())
            .replace("??", replaceE.random())
            .replace("e", replaceE.random())
            .replace("??", replaceE.random())
            .replace("??", replaceE.random())
            .replace("??", replaceE.random())
            .replace("??", replaceE.random())
            .replace("T", replaceT.random())
            .replace("t", replaceT.random())
            .replace("I", replaceI.random())
            .replace("??", replaceE.random())
            .replace("??", replaceE.random())
            .replace("??", replaceE.random())
            .replace("??", replaceE.random())
            .replace("i", replaceI.random())
            .replace("??", replaceE.random())
            .replace("??", replaceE.random())
            .replace("??", replaceE.random())
            .replace("??", replaceE.random())
            .replace("O", replaceO.random())
            .replace("??", replaceE.random())
            .replace("??", replaceE.random())
            .replace("??", replaceE.random())
            .replace("??", replaceE.random())
            .replace("o", replaceO.random())
            .replace("??", replaceE.random())
            .replace("??", replaceE.random())
            .replace("??", replaceE.random())
            .replace("??", replaceE.random())
            .replace("G", replaceG.random())
            .replace("g", replaceG.random())
            .replace("??", "U")
            .replace("??", replaceE.random())
            .replace("??", replaceE.random())
            .replace("??", replaceE.random())
            .replace("??", "u")
            .replace("??", replaceE.random())
            .replace("??", replaceE.random())
            .replace("??", replaceE.random())
    }
}