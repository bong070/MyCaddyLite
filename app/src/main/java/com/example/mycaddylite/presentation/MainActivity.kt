package com.example.mycaddylite.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.maps.android.compose.*
import com.google.android.gms.maps.model.LatLng
import androidx.compose.foundation.layout.fillMaxSize
import com.google.android.gms.maps.model.CameraPosition



class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val locationState = mutableStateOf<LatLng?>(null)

        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
                fetchLastLocation { latLng ->
                    locationState.value = latLng
                }
            }
        }

        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

        setContent {
            locationState.value?.let { userLocation ->
                GolfMap(userLocation)
            }
        }
    }

    private fun fetchLastLocation(callback: (LatLng) -> Unit) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) return

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    callback(LatLng(it.latitude, it.longitude))
                } ?: Log.e("MyCaddy", "❌ 위치를 가져올 수 없습니다.")
            }
    }
}

@Composable
fun GolfMap(userLocation: LatLng) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(userLocation, 17f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(isMyLocationEnabled = true)
    ) {
        Marker(
            state = MarkerState(position = userLocation),
            title = "현재 위치"
        )

        // 예시 홀 위치
        val holeLatLng = LatLng(37.4241, -122.0839)
        Marker(
            state = MarkerState(position = holeLatLng),
            title = "1번 홀"
        )
    }
}
