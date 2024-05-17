package com.example.cfs.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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


    var dateResult by mutableStateOf("Pick a date")
        private set


    var isDateFocused by mutableStateOf(false)
        private set

    var isExpanded by mutableStateOf(false)
        private set

    var selectedCourse by mutableStateOf("Choose Course")
        private set


    var topic by mutableStateOf("")
        private set

    fun updateDateResult(dateString: String) {
        dateResult = dateString
    }

    fun updateIsDateFocused(isDateFocused: Boolean) {
        this.isDateFocused = isDateFocused
    }

    fun updateIsExpanded(isExpanded: Boolean) {
        this.isExpanded = isExpanded
    }

    fun updateSelectedCourse(selectedCourse: String) {
        this.selectedCourse = selectedCourse
    }

    fun updateTopic(topic: String) {
        this.topic = topic
    }

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
            val courseCodeStringList = mutableListOf<String>()
            response.map { courseCodeStringList.add(it.courseCode) }
            _courseCodeList.emit(courseCodeStringList)
        }
    }

}