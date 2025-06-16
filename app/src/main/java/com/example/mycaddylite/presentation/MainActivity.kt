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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import android.util.Log

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyCaddyLiteTheme {
                val viewModel: GolfCourseViewModel = viewModel()
                val courseList by viewModel.courses.collectAsState()
                val error by viewModel.error.collectAsState()

                val context = LocalContext.current

                LaunchedEffect(Unit) {
                    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
                    if (ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        // getCurrentLocation 사용
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

                CourseListScreen(courseList, error)
            }
        }
    }
}