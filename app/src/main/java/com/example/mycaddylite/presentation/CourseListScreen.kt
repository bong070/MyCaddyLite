package com.example.mycaddylite.presentation

import androidx.compose.runtime.Composable
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.items
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.example.mycaddylite.data.model.GolfCourse
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

@Composable
fun CourseListScreen(
    courses: List<GolfCourse>,
    error: String?,
    isLoading: Boolean,
    onCourseClick: (GolfCourse) -> Unit
) {
    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize().background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text("Loading...", color = Color.White)
        }
    } else if (error != null) {
        Text("Error: $error", color = Color.Red)
    } else {
        ScalingLazyColumn(
            modifier = Modifier.fillMaxSize().background(Color.Black),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                Text("Select Golf Course", fontSize = 18.sp, color = Color.White)
            }
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
                        text = course.courseName,
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
