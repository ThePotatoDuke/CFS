package com.example.cfs.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cfs.data.supabase
import com.example.cfs.models.Course
import com.example.cfs.models.Feedback
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class ListViewModel : ViewModel() {
    private val _feedbackList = MutableStateFlow<List<Feedback>>(listOf())
    val feedbackList: Flow<List<Feedback>> = _feedbackList

    private val _courseCodeList = MutableStateFlow<List<Course>>(listOf())
    val courseCodeList: Flow<List<Course>> = _courseCodeList

    var isLoading by mutableStateOf(true)
        private set

    init {
        fetchData()
    }

    fun fetchData() {
        isLoading = true
        viewModelScope.launch(Dispatchers.IO) {
            coroutineScope {
                launch { getFeedbacks() }
                launch { getCourseCodes() }
            }
            isLoading = false
        }
    }

//    private suspend fun fetchFeedbacks(): List<Feedback> {
//        return supabase
//            .from("feedbacks")
//            .select(Columns.list("id", "course_topic", "course_date", "course_id", "summary"))
//            .decodeList<Feedback>()
//
//    }

    suspend fun getFeedbacks() {
        val response = supabase
            .from("feedbacks")
            .select(Columns.list("id", "course_topic", "course_date", "course_id", "summary"))
            .decodeList<Feedback>()
        _feedbackList.emit(response)

    }

//    private suspend fun fetchCourseCodes(): List<Course> {
//        return supabase
//            .from("courses")
//            .select(Columns.list("id", "course_code", "course_name"))
//            .decodeList<Course>()
//
//    }

    suspend fun getCourseCodes() {
        val response = supabase
            .from("courses")
            .select(Columns.list("id", "course_code", "course_name"))
            .decodeList<Course>()
        _courseCodeList.emit(response)
    }


}