package com.example.mycaddylite.ui.theme

import androidx.compose.runtime.Composable
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Typography
import androidx.wear.compose.material.Colors

@Composable
fun MyCaddyLiteTheme(
    content: @Composable () -> Unit
) {
    // Wear OS 기본 테마 적용
    MaterialTheme(
        colors = Colors(),
        typography = Typography(),
        content = content
    )
}
