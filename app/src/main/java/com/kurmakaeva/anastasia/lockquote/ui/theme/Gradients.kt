package com.kurmakaeva.anastasia.lockquote.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush

val pinkGradient = Brush.linearGradient(
    colors = listOf(accent, accentDark),
    start = Offset.Zero,
    end = Offset(0f, Float.POSITIVE_INFINITY)
)