package com.ericmong.school_system.dto

data class CourseDto(
    val courseId: Long,
    val courseName: String,
    val courseDescription: String,
    val courseCategory: String,
    val active: Boolean,
)
