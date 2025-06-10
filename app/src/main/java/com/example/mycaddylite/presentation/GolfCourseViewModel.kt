package com.example.mycaddylite.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycaddylite.data.GolfCourse
import com.example.mycaddylite.data.GolfCourseRepository
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
}
