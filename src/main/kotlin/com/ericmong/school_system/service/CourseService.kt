package com.ericmong.school_system.service

import com.ericmong.school_system.dto.CreateCourseRequest
import com.ericmong.school_system.dto.UpdateCourseRequest
import com.ericmong.school_system.model.Course
import java.util.*

interface CourseService {
    fun getActiveCourseCount(): Int;
    fun getCourseCount(): Int;
    fun getAllActiveCourses(page: Int, size: Int): List<Course>;
    fun getAllCourses(page: Int, size: Int): List<Course>;
    fun getCourseById(id: Long): Optional<Course>;
    fun createCourse(request: CreateCourseRequest): Course;
    fun updateCourse(id: Long, request: UpdateCourseRequest): Course?;
}