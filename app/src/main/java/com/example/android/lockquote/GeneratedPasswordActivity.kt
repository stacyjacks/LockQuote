package com.example.android.lockquote

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.text.SpannedString
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.activity_generated_password.*

class GeneratedPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generated_password)
        handleIntent(intent)

        setTitle(R.string.generated_pass_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val animation = findViewById<LottieAnimationView>(R.id.loadingPass)
        animation.addAnimatorListener(object: Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                val runningAnimation = findViewById<LottieAnimationView>(R.id.loadingPass)
                runningAnimation.visibility = View.GONE

                val justASecTV = findViewById<TextView>(R.id.justASec)
                justASecTV.visibility = View.GONE

                val mainLinearLayout = findViewById<LinearLayout>(R.id.main_content_linear_layout)
                mainLinearLayout.visibility = View.VISIBLE

                val bottomViewGroup = findViewById<LinearLayout>(R.id.bottom_view_group)
                bottomViewGroup.visibility = View.VISIBLE
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationRepeat(animation: Animator?) {

            }
        })

        val generatedPassTextView = findViewById<TextView>(R.id.generatedPass)
        val passwordString = firstCharOfEveryWordOf(selectedText()).joinToString("")
        val modPasswordString = charReplacer(passwordString)
        generatedPassTextView.text = modPasswordString

        val numberOfCharsTextView = findViewById<TextView>(R.id.numberOfCharacters)
        numberOfCharsTextView.text = numberOfCharCalculator(passwordString)

        val helpMeRemember = findViewById<Button>(R.id.helpMeRemember)
        helpMeRemember.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent
                .putExtra("modPasswordString", modPasswordString)
                .putExtra("selectedLyric", selectedText())
            startActivity(intent)
        }

        val tryAgain = findViewById<Button>(R.id.tryAgainButton)
        tryAgain.setOnClickListener {
            this.finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                this.onBackPressed()
            // Respond to the action bar's Up/Home button
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        selectedTextFromLyric.text = makeFirstLetterBold(selectedText())
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
            if (word.first().toString() == "[") {
                continue
            }
            if (word.last().toString() == "]") {
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

    private fun selectedText(): String {
        return intent.getStringExtra("selectedText")
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
            .replace("a", replaceA.random())
            .replace("S", replaceS.random())
            .replace("s", replaceS.random())
            .replace("E", replaceE.random())
            .replace("e", replaceE.random())
            .replace("T", replaceT.random())
            .replace("t", replaceT.random())
            .replace("I", replaceI.random())
            .replace("i", replaceI.random())
            .replace("O", replaceO.random())
            .replace("o", replaceO.random())
            .replace("G", replaceG.random())
            .replace("g", replaceG.random())
    }
}

fun firstCharOfEveryWordOf(selectedTextFromLyric: String): ArrayList<String> {
    val regex = Regex("(\\s+|\\\\n)")
    return ArrayList(
        selectedTextFromLyric
            .split(regex)
            .map { it.first().toString() }
            .filter { it != "[" }
    )
}

