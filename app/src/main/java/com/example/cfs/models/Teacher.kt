package com.example.cfs.models

import kotlinx.serialization.Serializable

@Serializable
data class Teacher(
    val id: Int? = null,
    val name: String,
    val surname: String,
    val mail: String
)
