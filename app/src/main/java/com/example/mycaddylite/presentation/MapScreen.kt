package com.example.mycaddylite.presentation

import android.annotation.SuppressLint
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.mycaddylite.data.GolfCourse
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

@Composable
fun MapScreen(
    userLat: Double,
    userLng: Double,
    courses: List<GolfCourse>,
    lifecycleOwner: LifecycleOwner
) {
    val mapView = rememberMapViewWithLifecycle(lifecycleOwner)

    AndroidView(factory = { mapView }) { mv ->
        mv.getMapAsync(OnMapReadyCallback { googleMap ->
            // 사용자 위치 마커
            val userLatLng = LatLng(userLat, userLng)
            googleMap.addMarker(
                MarkerOptions()
                    .position(userLatLng)
                    .title("현재 위치")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            )

            // 골프 코스 마커
            courses.forEach { course ->
                val lat = course.latitude.toDoubleOrNull()
                val lng = course.longitude.toDoubleOrNull()
                if (lat != null && lng != null) {
                    googleMap.addMarker(
                        MarkerOptions()
                            .position(LatLng(lat, lng))
                            .title(course.courseName)
                    )
                }
            }

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 11f))
        })
    }
}

@Composable
fun rememberMapViewWithLifecycle(lifecycleOwner: LifecycleOwner): MapView {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }

    DisposableEffect(lifecycleOwner) {
        val observer = object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) = mapView.onCreate(null)
            override fun onStart(owner: LifecycleOwner) = mapView.onStart()
            override fun onResume(owner: LifecycleOwner) = mapView.onResume()
            override fun onPause(owner: LifecycleOwner) = mapView.onPause()
            override fun onStop(owner: LifecycleOwner) = mapView.onStop()
            override fun onDestroy(owner: LifecycleOwner) = mapView.onDestroy()
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    return mapView
}
