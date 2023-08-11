package com.kurmakaeva.anastasia.lockquote.ui.common

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kurmakaeva.anastasia.lockquote.ui.theme.accent
import com.kurmakaeva.anastasia.lockquote.ui.theme.darkGrey
import com.kurmakaeva.anastasia.lockquote.ui.theme.lightGrey
import com.kurmakaeva.anastasia.lockquote.ui.theme.white

@Composable
fun RegularButton(onClick: () -> Unit, modifier: Modifier, buttonText: String, enabled: Boolean = true) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = accent,
            contentColor = white,
            disabledBackgroundColor = darkGrey,
            disabledContentColor = lightGrey
        )
    ) {
        Text(
            text = buttonText.uppercase(),
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun PreviewRegularButton() {
    RegularButton(
        onClick = { /* preview only */ },
        modifier = Modifier,
        buttonText = "try again",
        enabled = false
    )
}