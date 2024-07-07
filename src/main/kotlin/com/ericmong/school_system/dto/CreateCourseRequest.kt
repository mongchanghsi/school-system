package com.ericmong.school_system.dto

import jakarta.validation.constraints.NotBlank

data class CreateCourseRequest(
    @field:NotBlank(message = "Course Name is mandatory")
    val name: String,

    @field:NotBlank(message = "Course Description is mandatory")
    val description: String,

    @field:NotBlank(message = "Course Category is mandatory")
    val category: String,

    val remarks: String,
)
