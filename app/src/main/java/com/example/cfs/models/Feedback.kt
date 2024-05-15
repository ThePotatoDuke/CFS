package com.example.cfs.models

import com.example.cfs.OffsetDateTimeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
data class Feedback(
    val id: Int? = null, // TODO idk how to deal with these for now

    @Serializable(with = OffsetDateTimeSerializer::class)
    @SerialName("course_date")
    val course_date: OffsetDateTime,

    @SerialName("course_topic")
    val course_topic: String,
    val summary: String? = null,
    val url: String? = null,

    @SerialName("course_id")
    val course_id: Int? = null,

    @Serializable(with = OffsetDateTimeSerializer::class)
    @SerialName("feedback_start_date")
    val feedback_start_date: OffsetDateTime? = null, // assigned automagically by db
)