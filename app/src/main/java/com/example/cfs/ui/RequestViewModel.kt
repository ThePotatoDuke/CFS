package com.example.cfs.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cfs.data.supabase
import com.example.cfs.models.Course
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RequestViewModel : ViewModel() {

    private val _courseCodeList = MutableStateFlow<List<String>>(listOf())
    val courseCodeList: Flow<List<String>> = _courseCodeList

    private var _dateResult = MutableStateFlow("Pick a date")
    var dateResult: Flow<String> = _dateResult

    private var _isDateFocused = MutableStateFlow(false)
    var isDateFocused: Flow<Boolean> = _isDateFocused

    private val _isExpanded = MutableStateFlow(false)
    val isExpanded: Flow<Boolean> = _isExpanded

    private var _selectedCourse = MutableStateFlow("Choose Course")
    var selectedCourse: Flow<String> = _selectedCourse

    private val _topic = MutableStateFlow("")
    val topic: Flow<String> = _topic

    init {
        getCourseCodes()
    }

    private suspend fun fetchCourseCodes(): List<Course> {
        return supabase
            .from("courses")
            .select(Columns.list("id", "course_code", "course_name"))
            .decodeList<Course>()

    }


    private fun getCourseCodes() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = fetchCourseCodes()
            val courseCodeStringList = mutableListOf("")
            response.map { courseCodeStringList.add(it.courseCode) }
            _courseCodeList.emit(courseCodeStringList)
        }
    }
}