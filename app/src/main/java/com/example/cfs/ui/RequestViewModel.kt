package com.example.cfs.ui

import android.os.Build
import androidx.annotation.RequiresApi
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
import java.time.OffsetDateTime
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
class RequestViewModel : ViewModel() {

    private val _courseCodeList = MutableStateFlow<List<String>>(listOf())
    val courseCodeList: Flow<List<String>> = _courseCodeList


    var dateResult by mutableStateOf<OffsetDateTime>(OffsetDateTime.now())
        private set


    var isDateFocused by mutableStateOf(false)
        private set

    var isExpanded by mutableStateOf(false)
        private set

    var selectedCourse by mutableStateOf("Choose Course")
        private set


    var topic by mutableStateOf("")
        private set

    fun updateDateResult(offsetDate: OffsetDateTime) {
        dateResult = offsetDate
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

    fun activateFeedback() {
        viewModelScope.launch(Dispatchers.Default) {
            activate()
            // clear fields as a response
            clearFields()
        }
    }
    private suspend fun activate() {
        // first fetch the course from selectedCourse
        val course = supabase
            .from("courses")
            .select {
                filter {
                    eq("course_code", selectedCourse)
                }
            }
            .decodeSingle<Course>()

        // then create a feedback
        val feedback = Feedback(
            course_date = dateResult,
            course_id = course.id,
            course_topic = topic,
            feedback_start_date = OffsetDateTime.now(ZoneId.systemDefault()),
            url = "http://localhost:8080/feedback/${course.courseCode}"
        )

        supabase.from("feedbacks").insert(feedback)
    }
    private fun clearFields() {
        dateResult = OffsetDateTime.now()
        topic = ""
        selectedCourse = ""
    }
}