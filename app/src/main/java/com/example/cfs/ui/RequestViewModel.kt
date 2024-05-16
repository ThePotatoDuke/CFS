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

//    private var _dateResult = MutableStateFlow("Pick a date")
//    var dateResult: Flow<String> = _dateResult

    var dateReault by mutableStateOf("Pick aaaaaaaaaaaaa date")
        private set

//    private var _isDateFocused = MutableStateFlow(false)
//    var isDateFocused: Flow<Boolean> = _isDateFocused

    var isDateFocused by mutableStateOf(false)
        private set

//    private val _isExpanded = MutableStateFlow(false)
//    val isExpanded: Flow<Boolean> = _isExpanded

    var isExpanded by mutableStateOf(false)
        private set


//    private var _selectedCourse = MutableStateFlow("Choose Course")
//    var selectedCourse: Flow<String> = _selectedCourse

    var selectedCourse by mutableStateOf("Choose Course")
        private set

//    private val _topic = MutableStateFlow("")
//    val topic: Flow<String> = _topic


    var topic by mutableStateOf("")
        private set
//    fun updateDateResult(dateString: String) {
//
//    }
//    fun updateIsDateFocused(dateString: String) {
//
//    }
//    fun updateIsExpanded(dateString: String) {
//
//    }
//    fun updateSelectedCourse(dateString: String) {
//
//    }

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