package com.example.cfs.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cfs.data.supabase
import com.example.cfs.models.Course
import com.example.cfs.models.Feedback
import com.example.cfs.models.Teacher
import com.example.cfs.ui.theme.globalMail
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

    private var authenticatedTeacherId: Int? = null

    var isLoading by mutableStateOf(true)
        private set


    fun fetchData() {
        isLoading = true
        viewModelScope.launch(Dispatchers.IO) {
            coroutineScope {
                authenticatedTeacherId = getTeacherId()
                launch { getAuthenticatedTeachersFeedbacks() }
                launch { getAuthenticatedTeachersCourseCodes() }
            }
            isLoading = false
        }
    }


    private suspend fun getAuthenticatedTeachersFeedbacks() {
        val response = supabase
            .from("feedbacks")
            .select(Columns.raw("id, course_topic, course_date, course_id, summary,courses!inner(id,course_code,course_name,teacher_id)")) {
                filter {
                    eq("courses.teacher_id", authenticatedTeacherId!!)
                }
            }
            .decodeList<Feedback>()
        _feedbackList.emit(response)
    }


    private suspend fun getAuthenticatedTeachersCourseCodes() {
        val response = supabase
            .from("courses")
            .select(Columns.list("id", "course_code", "course_name", "teacher_id")) {
                filter {
                    eq("teacher_id", authenticatedTeacherId!!)
                }
            }

            .decodeList<Course>()
        _courseCodeList.emit(response)
    }

    suspend fun getTeacherId(): Int? {
        val teacher = supabase
            .from("teachers")
            .select(Columns.raw("id,name,surname,mail")) {
                filter {
                    eq("mail", globalMail)
                }
            }.decodeSingle<Teacher>()
        return teacher.id
    }
}