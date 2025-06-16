package com.example.mycaddylite.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.Text
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.background
import androidx.compose.ui.Modifier

@Composable
fun CourseDetailScreen(courseId: String) {
    // 여기서 이후 API 호출로 홀 정보 가져오기 작업 들어감
    Text(
        text = "Course ID: $courseId",
        modifier = Modifier.fillMaxSize().background(Color.Black),
        color = Color.White
    )
}
