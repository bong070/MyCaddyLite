package com.example.mycaddylite.presentation

import androidx.compose.runtime.Composable
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.items
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.example.mycaddylite.data.model.GolfCourse
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Alignment

@Composable
fun CourseListScreen(
    courses: List<GolfCourse>,
    error: String?,
    isLoading: Boolean,
    onCourseClick: (GolfCourse) -> Unit
) {
    if (isLoading) {
        // 로딩 중일 때만 표시
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Loading...", color = Color.White, fontSize = 18.sp)
        }
    }else if (error != null) {
        Text(text = "에러: $error", color = Color.White)
    } else {
        ScalingLazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentPadding = PaddingValues(
                top = 16.dp, bottom = 16.dp
            )
        ) {
            item {
                Text(
                    text = "Select Golf Course",
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
            when {
                isLoading -> {
                    item {
                        Text(
                            text = "Loading...",
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                    }
                }
                error != null -> {
                    item {
                        Text(
                            text = "Error: $error",
                            fontSize = 16.sp,
                            color = Color.Red
                        )
                    }
                }
                courses.isEmpty() -> {
                    item {
                        Text(
                            text = "No courses found",
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }
                } else -> {
                    items(courses) { course ->
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                                .background(Color.DarkGray, shape = RoundedCornerShape(12.dp))
                                .clickable { onCourseClick(course) }
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = course.courseName ?: "Unknown",
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                fontSize = 15.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}
