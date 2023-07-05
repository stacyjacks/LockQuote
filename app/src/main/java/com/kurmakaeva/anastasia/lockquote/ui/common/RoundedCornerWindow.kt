package com.kurmakaeva.anastasia.lockquote.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kurmakaeva.anastasia.lockquote.R
import com.kurmakaeva.anastasia.lockquote.ui.theme.white

@Composable
fun RoundedCornerWindow(content: @Composable() () -> Unit) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .background(
                color = white,
                shape = RoundedCornerShape(24.dp)
            ),
        verticalAlignment = Alignment.Top
    ) {
       Text(
           text = stringResource(id = R.string.take_a_good_look),
           modifier = Modifier.padding(24.dp),
           fontSize = 18.sp
       )
        content()
    }
}

@Composable
@Preview
fun Preview() {
    RoundedCornerWindow() {}
}