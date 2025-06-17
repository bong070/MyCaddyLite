package com.example.mycaddylite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycaddylite.data.model.GolfCourse
import com.example.mycaddylite.data.repository.GolfCourseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GolfCourseViewModel : ViewModel() {
    private val repository = GolfCourseRepository()

    private val _courses = MutableStateFlow<List<GolfCourse>>(emptyList())
    val courses: StateFlow<List<GolfCourse>> = _courses

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadCourses(latitude: String, longitude: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val result = repository.getNearbyCourses(latitude, longitude)
                _courses.value = result.take(5)
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}
