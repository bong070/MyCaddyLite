package com.example.mycaddylite.presentation

import androidx.compose.runtime.Composable
import androidx.wear.compose.material.Text
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CourseDetailScreen(courseId: String) {
    Text(
        text = "Course ID: $courseId",
        modifier = Modifier.fillMaxSize().background(Color.Black),
        color = Color.White
    )
}
