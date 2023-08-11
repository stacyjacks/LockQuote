package com.kurmakaeva.anastasia.lockquote.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kurmakaeva.anastasia.lockquote.R
import com.kurmakaeva.anastasia.lockquote.ui.theme.lightGrey
import com.kurmakaeva.anastasia.lockquote.ui.theme.white

@Composable
fun SkeletonListItem() {
    Row(modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth()
        .background(
            color = white,
            shape = RoundedCornerShape(24.dp)
        )
        .padding(4.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.lockquote_bw),
            contentDescription = null,
            modifier = Modifier
                .padding(8.dp)
                .width(60.dp)
        )
        Column {
            Column(modifier = Modifier
                .padding(8.dp)
                .background(
                    color = lightGrey,
                    shape = RoundedCornerShape(18.dp)
                )
                .fillMaxWidth()
                .height(20.dp)
            ) {}
            Column(modifier = Modifier
                .padding(8.dp)
                .background(
                    color = lightGrey,
                    shape = RoundedCornerShape(18.dp)
                )
                .width(250.dp)
                .height(20.dp)
            ) {}
        }
    }
}

@Composable
@Preview
private fun PreviewSkeletonList() {
    SkeletonListItem()
}