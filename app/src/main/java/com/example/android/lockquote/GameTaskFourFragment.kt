package com.example.android.lockquote

import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import cdflynn.android.library.checkview.CheckView


class GameTaskFourFragment : Fragment(), OnDataPass {

    lateinit var dataPass: OnDataPass
    private lateinit var radioGroup: RadioGroup
    private lateinit var correctAnswerButton: RadioButton
    private lateinit var incorrectAnswerButton1: RadioButton
    private lateinit var incorrectAnswerButton3: RadioButton
    private lateinit var incorrectAnswerButton4: RadioButton

    companion object {
        fun newInstance(): GameTaskFourFragment {
            return GameTaskFourFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game_task_four, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        radioGroup = view.findViewById(R.id.multipleChoiceTaskFour)
        correctAnswerButton = view.findViewById(R.id.radioButton2)
        incorrectAnswerButton1 = view.findViewById(R.id.radioButton1)
        incorrectAnswerButton3 = view.findViewById(R.id.radioButton3)
        incorrectAnswerButton4 = view.findViewById(R.id.radioButton4)

        correctAnswerButton.text = passwordString()
        val shuffledPasswordString = passwordString()
            .toCharArray()
            .toMutableList()
            .shuffled()
            .toString()
            .replace("[\\[\\]]", "")
            .replace(",", "")
            .replace("]", "")
            .replace("[", "")
            .replace(" ", "")
        incorrectAnswerButton1.text = shuffledPasswordString
        incorrectAnswerButton3.text = passwordString().decapitalize()
        incorrectAnswerButton4.text = passwordString().reversed()

        onRadioButtonClicked(view)
        onContinueTapped()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPass = context as OnDataPass
    }

    override fun onDataPass(data: String) {
        dataPass.onDataPass(data)
    }

    override fun passwordString(): String {
        return dataPass.passwordString()
    }

    override fun selectedLyric(): String {
        return dataPass.selectedLyric()
    }

    private fun onRadioButtonClicked(view: View) {
        radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            when {
                correctAnswerButton.isChecked ->
                    correctAnswerButton.background = ContextCompat.getDrawable(correctAnswerButton.context, R.drawable.bubble)
                incorrectAnswerButton1.isChecked ->
                    incorrectAnswerButton1.background = ContextCompat.getDrawable(incorrectAnswerButton1.context, R.drawable.bubble_red)
                incorrectAnswerButton3.isChecked ->
                    incorrectAnswerButton3.background = ContextCompat.getDrawable(incorrectAnswerButton3.context, R.drawable.bubble_red)
                incorrectAnswerButton4.isChecked ->
                    incorrectAnswerButton4.background = ContextCompat.getDrawable(incorrectAnswerButton4.context, R.drawable.bubble_red)

            }
            if (correctAnswerButton.isChecked) {
                onCorrectChoice()
                incorrectAnswerButton1.visibility = View.GONE
                incorrectAnswerButton3.visibility = View.GONE
                incorrectAnswerButton4.visibility = View.GONE

            }
        }
    }

    private fun onCorrectChoice() {
        val checkAnimation = view?.findViewById<CheckView>(R.id.checkViewAnimation)
        checkAnimation?.visibility = CheckView.VISIBLE
        checkAnimation?.check()
        val continueButton = view?.findViewById<Button>(R.id.continueButtonTaskFour)
        continueButton?.visibility = View.VISIBLE
    }

    private fun onContinueTapped() {
        val continueButton = view?.findViewById<Button>(R.id.continueButtonTaskFour)
        continueButton?.setOnClickListener {
            val fragmentTaskFive = GameTaskFiveFragment.newInstance()
            val transaction = fragmentManager?.beginTransaction()
            transaction
                ?.replace(R.id.frameFragmentGame, fragmentTaskFive)
                ?.addToBackStack(null)
                ?.commit()
        }
    }
}