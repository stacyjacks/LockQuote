package com.example.android.lockquote

import android.content.Intent
import android.os.Bundle
import android.text.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import kotlinx.android.synthetic.main.activity_generated_password.*

class GeneratedPasswordActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generated_password)
        handleIntent(intent)

        val generatedPassTextView = findViewById<TextView>(R.id.generatedPass)
        generatedPassTextView.text = firstCharOfEveryWordOf(selectedText())
            .joinToString("")
            .replace("A", "4")
            .replace("E", "3")
            .replace("I", "1")
            .replace("S", "5")
            .replace("T", "7")
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        selectedTextFromLyric.text = makeFirstLetterBold(selectedText())
    }

    private fun makeFirstLetterBold(selectedLyric: String): SpannedString {
        var customString = SpannedString("")
        val lyricWordsArray =  selectedLyric.split(" ").toTypedArray()
        for (word in lyricWordsArray) {
            if (word.isBlank()) { continue }
            if (word.first().toString() == "[") { continue }
            if (word.last().toString() == "]") { continue }

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
}

fun firstCharOfEveryWordOf(selectedTextFromLyric: String): ArrayList<String> {
    return ArrayList(
        selectedTextFromLyric
            .split(" ")
            .map { it.first().toString() }
    )
}

