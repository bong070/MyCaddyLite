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
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable

@Composable
fun CourseListScreen(courses: List<GolfCourse>, error: String?, onCourseSelected: (String) -> Unit) {
    if (error != null) {
        Text(text = "에러: $error", color = Color.White)
    } else {
        ScalingLazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
        ) {
            item {
                Text(
                    text = "Select Golf Course",
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
            items(courses) { course ->
                Text(
                    text = course.courseName.orEmpty(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier.clickable {
                        onCourseSelected(course.clubId.toString()) // id를 넘겨준다
                    }
                )
            }
        }
    }
}

