package com.example.mycaddylite.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.wear.compose.material.Text
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.delay
import kotlin.math.*

@Composable
fun CourseDistanceScreen(targetLat: Double, targetLng: Double, courseName: String) {
    val context = LocalContext.current
    var distance by remember { mutableStateOf<Double?>(null) }

    LaunchedEffect(Unit) {
        while (true) {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                fusedLocationClient.getCurrentLocation(com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY, null)
                    .addOnSuccessListener { location: Location? ->
                        location?.let {
                            distance = calculateDistance(it.latitude, it.longitude, targetLat, targetLng)
                        }
                    }
            }
            delay(3000) // 3초마다 갱신
        }
    }

    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = courseName, fontSize = 16.sp, color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))
            if (distance != null) {
                Text(text = "거리: ${"%.1f".format(distance)} m", fontSize = 18.sp, color = Color.White)
            } else {
                Text(text = "측정 중...", fontSize = 16.sp, color = Color.Gray)
            }
        }
    }
}

fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val R = 6371000.0 // Earth radius in meters
    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)
    val a = sin(dLat/2) * sin(dLat/2) +
            cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
            sin(dLon/2) * sin(dLon/2)
    val c = 2 * atan2(sqrt(a), sqrt(1-a))
    return R * c
}
