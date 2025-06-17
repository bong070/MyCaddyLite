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

                LaunchedEffect(Unit) {
                    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
                    if (ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                            .addOnSuccessListener { location: Location? ->
                                location?.let {
                                    viewModel.loadCourses(
                                        it.latitude.toString(),
                                        it.longitude.toString()
                                    )
                                }
                            }
                    }
                }

                NavHost(navController = navController, startDestination = "list") {
                    composable("list") {
                        CourseListScreen(courseList, error, isLoading) { selectedCourse ->
                            navController.navigate("distance/${selectedCourse.latitude}/${selectedCourse.longitude}/${selectedCourse.courseName}")
                        }
                    }
                    composable(
                        "distance/{latitude}/{longitude}/{courseName}",
                        arguments = listOf(
                            navArgument("latitude") { type = NavType.StringType },
                            navArgument("longitude") { type = NavType.StringType },
                            navArgument("courseName") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val latitude = backStackEntry.arguments?.getString("latitude")?.toDouble() ?: 0.0
                        val longitude = backStackEntry.arguments?.getString("longitude")?.toDouble() ?: 0.0
                        val courseName = backStackEntry.arguments?.getString("courseName") ?: ""
                        CourseDistanceScreen(latitude, longitude, courseName)
                    }
                }
            }
        }
    }
}
