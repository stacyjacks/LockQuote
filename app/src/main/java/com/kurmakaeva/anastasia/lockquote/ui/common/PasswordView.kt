package com.kurmakaeva.anastasia.lockquote.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.kurmakaeva.anastasia.lockquote.ui.theme.largeText

@Composable
fun PasswordView(text: String) {
    val showPwd = remember { mutableStateOf(false) }

    Text(
        text = if (showPwd.value) text else ASTERISK.repeat(text.length),
        modifier = Modifier.clickable { showPwd.value = !showPwd.value },
        style = largeText
    )
}