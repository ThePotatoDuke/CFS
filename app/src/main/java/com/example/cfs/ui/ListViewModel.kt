package com.example.cfs.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cfs.data.supabase
import com.example.cfs.models.Course
import com.example.cfs.models.Feedback
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class ListViewModel : ViewModel() {
    private val _feedbackList = MutableStateFlow<List<Feedback>>(listOf())
    val feedbackList: Flow<List<Feedback>> = _feedbackList

    private val _courseCodeList = MutableStateFlow<List<Course>>(listOf())
    val courseCodeList: Flow<List<Course>> = _courseCodeList


    init {
        getCourseCodes()
        getFeedbacks()

    }


    private suspend fun fetchFeedbacks(): List<Feedback> {
        return supabase
            .from("feedbacks")
            .select(Columns.list("id", "course_topic", "course_date", "course_id", "summary"))
            .decodeList<Feedback>()

    }

    private fun getFeedbacks() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = fetchFeedbacks()
            _feedbackList.emit(response)
        }
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
            _courseCodeList.emit(response)
        }
    }


}