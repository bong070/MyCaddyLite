package com.example.mycaddylite.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycaddylite.data.repository.GolfCourseRepository
import com.example.mycaddylite.data.model.GolfCourse
import com.example.mycaddylite.data.model.GolfCourseDetailResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GolfCourseViewModel(
    private val repository: GolfCourseRepository = GolfCourseRepository()
) : ViewModel() {

    private val _courses = MutableStateFlow<List<GolfCourse>>(emptyList())
    val courses: StateFlow<List<GolfCourse>> = _courses

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _selectedCourse = MutableStateFlow<GolfCourseDetailResponse?>(null)
    val selectedCourse: StateFlow<GolfCourseDetailResponse?> = _selectedCourse

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadCoursesNearby(latitude: String, longitude: String) {
        viewModelScope.launch {
            try {
                val result = repository.getNearbyCourses(latitude, longitude)
                _courses.value = result
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun loadCourses(lat: String, lng: String) {
        Log.d("GolfCourseViewModel", "API 호출 시작")

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val courseList = repository.getNearbyCourses(lat, lng)
                Log.d("GolfCourseViewModel", "API 성공: ${courseList.size}개 수신됨")

                _courses.value = courseList.take(5)
                _error.value = null
            } catch (e: Exception) {
                Log.e("GolfCourseViewModel", "Exception: ${e.message}")
                _error.value = "Network Error: ${e.message}"
            } finally {
                _isLoading.value = false;
            }
        }
    }

    fun loadCourseDetails(id: String) {
        viewModelScope.launch {
            try {
                val details = repository.getGolfCourseDetails(id)
                _selectedCourse.value = details
            } catch (e: Exception) {
                // 에러 처리
            }
        }
    }
}