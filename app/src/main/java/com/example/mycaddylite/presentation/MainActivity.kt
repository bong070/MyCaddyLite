package com.example.mycaddylite.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mycaddylite.ui.theme.MyCaddyLiteTheme
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import android.util.Log
import com.example.mycaddylite.viewmodel.GolfCourseViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyCaddyLiteTheme {
                val viewModel: GolfCourseViewModel = viewModel()
                val courseList by viewModel.courses.collectAsState()
                val error by viewModel.error.collectAsState()
                val isLoading by viewModel.isLoading.collectAsState()

                val context = LocalContext.current
                val navController = rememberNavController()

                // 위치 가져오기
                LaunchedEffect(Unit) {
                    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
                    if (ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                            .addOnSuccessListener { location: Location? ->
                                if (location != null) {
                                    Log.d("MainActivity", "위도 경도: ${location.latitude}, ${location.longitude}")
                                    viewModel.loadCourses(
                                        location.latitude.toString(),
                                        location.longitude.toString()
                                    )
                                } else {
                                    Log.d("MainActivity", "현재 위치를 가져오지 못함 (getCurrentLocation)")
                                }
                            }
                    } else {
                        Log.e("MainActivity", "위치 권한이 없습니다.")
                    }
                }

                // 네비게이션 설정
                NavHost(navController = navController, startDestination = "list") {
                    composable("list") {
                        CourseListScreen(courseList, error, isLoading) { selectedCourse ->
                            Log.d("MainActivity", "클릭된 골프장: ${selectedCourse.courseName}")
                        }
                    }
                    composable(
                        "detail/{courseId}",
                        arguments = listOf(navArgument("courseId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val courseId = backStackEntry.arguments?.getString("courseId") ?: ""
                        CourseDetailScreen(courseId)
                    }
                }
            }
        }
    }
}
