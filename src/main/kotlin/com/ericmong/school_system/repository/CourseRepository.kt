package com.ericmong.school_system.repository

import com.ericmong.school_system.model.Course
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CourseRepository: JpaRepository<Course, Long> {
    @Query("SELECT COUNT(*) FROM courses WHERE active = TRUE", nativeQuery = true)
    fun getActiveCount(): Int;

    @Query("SELECT * FROM courses", nativeQuery = true)
    fun getCoursesPageable(pageable: Pageable): Page<Course>;

    @Query("SELECT * FROM courses WHERE active = TRUE", nativeQuery = true)
    fun getActiveCoursesPageable(pageable: Pageable): Page<Course>;
}