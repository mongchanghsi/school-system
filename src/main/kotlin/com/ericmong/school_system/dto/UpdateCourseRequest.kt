package com.ericmong.school_system.dto

import jakarta.validation.constraints.NotBlank

data class UpdateCourseRequest(
    val name: String,
    val description: String,
    val category: String,
    val remarks: String,
    val active: Boolean,
)
