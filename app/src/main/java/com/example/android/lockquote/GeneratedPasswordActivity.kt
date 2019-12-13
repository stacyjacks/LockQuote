package com.example.android.lockquote

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.*
import android.text.style.StyleSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import kotlinx.android.synthetic.main.activity_generated_password.*

class GeneratedPasswordActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generated_password)
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        val selectedText = intent.getStringExtra("selectedText")
        selectedTextFromLyric.text = makeFirstLetterBold(selectedText)
    }

    private fun makeFirstLetterBold(selectedLyric: String): SpannedString {
        var customString = SpannedString("")
        val lyricWordsArray =  selectedLyric.split(" ").toTypedArray()
        for (i in lyricWordsArray) {
            val firstLetterBold = i.first()
            val normalText = i.drop(1)
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
}

