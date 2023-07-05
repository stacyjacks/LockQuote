package com.kurmakaeva.anastasia.lockquote.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kurmakaeva.anastasia.lockquote.R
import com.kurmakaeva.anastasia.lockquote.ui.theme.accent
import com.kurmakaeva.anastasia.lockquote.ui.theme.white

@Composable
fun ErrorScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = accent),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.broken_heart),
            contentDescription = "",
            modifier = Modifier
                .padding(top = 50.dp)
                .size(100.dp, 100.dp)
        )
        Text(
            text = stringResource(id = R.string.error_something_wrong),
            modifier = Modifier.padding(horizontal = 50.dp, vertical = 50.dp),
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            color = white
        )
        Image(
            painter = painterResource(id = R.drawable.broken_heart),
            contentDescription = "",
            modifier = Modifier
                .padding(bottom = 50.dp)
                .size(100.dp, 100.dp)
        )
    }
}

@Composable
@Preview
private fun PreviewErrorScreen() {
    ErrorScreen()
}