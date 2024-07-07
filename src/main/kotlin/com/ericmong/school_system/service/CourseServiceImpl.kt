package com.ericmong.school_system.service

import com.ericmong.school_system.dto.CreateCourseRequest
import com.ericmong.school_system.dto.UpdateCourseRequest
import com.ericmong.school_system.model.Course
import com.ericmong.school_system.repository.CourseRepository
import mu.KotlinLogging
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.util.*

@Service
class CourseServiceImpl(
    val courseRepository: CourseRepository
): CourseService {
    private val logger = KotlinLogging.logger {};

    override fun getActiveCourseCount(): Int {
        return courseRepository.getActiveCount();
    }

    override fun getCourseCount(): Int {
        return courseRepository.count().toInt();
    }

    override fun getAllActiveCourses(page: Int, size: Int): List<Course> {
        logger.info("Retrieving all active courses - Page: $page | Size: $size");
        val pageable = PageRequest.of(page - 1, size);
        return courseRepository.getCoursesPageable(pageable).content.toList();
    }

    override fun getAllCourses(page: Int, size: Int): List<Course> {
        logger.info("Retrieving all courses - Page: $page | Size: $size");
        val pageable = PageRequest.of(page - 1, size);
        return courseRepository.getActiveCoursesPageable(pageable).content.toList();
    }

    override fun getCourseById(id: Long): Optional<Course> {
        return courseRepository.findById(id);
    }

    override fun createCourse(request: CreateCourseRequest): Course {
        logger.info("Creating new course record - ${request.name}");
        // TODO: Check if course name is duplicated
        val courseToBeSaved = request.toEntity();
        return courseRepository.save(courseToBeSaved);
    }

    override fun updateCourse(id: Long, request: UpdateCourseRequest): Course? {
        logger.info("Updating course record - ${id}");
        // TODO: Check if course name is duplicated
        val course = getCourseById(id);
        if (course.isPresent) {
            val savedStudent = courseRepository.save(Course(
                id = id,
                name = request.name ?: course.get().name,
                description = request.description ?: course.get().description,
                category = request.category ?: course.get().category,
                active = request.active ?: course.get().active,
                remarks = request.remarks ?: course.get().remarks,
            ));
            return courseRepository.save(savedStudent);
        } else {
            return null;
        }
    }

    private fun CreateCourseRequest.toEntity(): Course {
        return Course(0, name, description, category, true, remarks)  // ID is set to 0 for new entity creation
    }
}