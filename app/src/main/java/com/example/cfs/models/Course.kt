package com.example.cfs.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Course(
    val id: Int? = null,
    @SerialName("course_name")
    val courseName: String,
    @SerialName("course_code")
    val courseCode: String,
    @SerialName("teacher_id")
    val teacher: Int? = null,

    @SerialName("feedbacks")
    val feedbacks: List<Feedback>? = null,

    )