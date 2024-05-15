package com.example.cfs.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ListViewModel : ViewModel() {
    private val _feedbackList = MutableStateFlow<List<Feedback>>(listOf())
    val feedbackList: Flow<List<Feedback>> = _feedbackList


    var courseCode: String by mutableStateOf("")
        private set

    init {
        getFeedbacks()

    }

    @Composable
    fun FeedbackList() {

        LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {
                _feedbackList.emit(
                    supabase.from("countries")
                        .select().decodeList<Feedback>()
                )
            }
        }

    }

    suspend fun fetchFeedbacks(): List<Feedback> {
        return supabase
            .from("feedbacks")
            .select(Columns.list("id", "course_topic", "course_date"))
            .decodeList<Feedback>()

    }

    private fun getFeedbacks() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = fetchFeedbacks()
            _feedbackList.emit(response)
        }
    }

    private suspend fun fetchCourseCode(course_id: Int?): String {
        return supabase
            .from("courses")
            .select(Columns.list("course_code")) {
                filter {
                    if (course_id != null) {
                        eq("course_code", course_id)
                    }
                }
            }
            .decodeSingle<Course>().courseCode


    }

    fun getCourseCode(course_id: Int?): String {

        viewModelScope.launch(Dispatchers.IO) {
            courseCode = fetchCourseCode(course_id)

        }
        return courseCode
    }


}