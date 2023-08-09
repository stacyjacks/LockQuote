package com.kurmakaeva.anastasia.lockquote.ui.common

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

fun makeFirstLetterBold(text: String): AnnotatedString {
    val annotatedString = buildAnnotatedString {
        val boldLetter = SpanStyle(
            fontWeight = FontWeight.Bold
        )
        val regularLetter = SpanStyle(
            fontWeight = FontWeight.Normal
        )

        val regex = Regex("(\\s|\\\\n)")

        val lyric = text.split(regex)

        withStyle(boldLetter) {
            lyric.forEach {
                append(it.first())
                withStyle(regularLetter) {
                    append(it.drop(1))
                    append(" ")
                }
            }
        }
    }

    return annotatedString
}