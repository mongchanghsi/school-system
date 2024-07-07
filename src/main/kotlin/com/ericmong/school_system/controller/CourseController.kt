package com.ericmong.school_system.controller

import com.ericmong.school_system.dto.CourseDto
import com.ericmong.school_system.dto.CreateCourseRequest
import com.ericmong.school_system.dto.PaginationResponse
import com.ericmong.school_system.dto.UpdateCourseRequest
import com.ericmong.school_system.model.Course
import com.ericmong.school_system.service.CourseService
import io.swagger.v3.oas.annotations.Hidden
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*
import java.util.function.Consumer

@RestController
@RequestMapping(path = ["/api/v1/course"])
@Tag(name = "Course")
class CourseController(
    val courseService: CourseService
) {
    @Operation(
        description = "Get all courses whether is it just active or both inactive and active. Pagination is supported.",
        summary = "Get all courses",
        responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200"
            )
        ],
        deprecated = false
    )
    @GetMapping
    fun getCourses(@RequestParam active: Boolean = true, @RequestParam page: Int = 1, @RequestParam size: Int = 1000): ResponseEntity<PaginationResponse<CourseDto>> {
        val courseCount = if (active) courseService.getActiveCourseCount() else courseService.getCourseCount();
        val courseData = if (active) courseService.getAllActiveCourses(page, size) else courseService.getAllCourses(page, size);
        val courseProcessedData = courseData.map { course: Course -> CourseDto(
            courseId = course.id,
            courseName = course.name,
            courseDescription = course.description,
            courseCategory = course.category,
            active = course.active
        )  };
        val response = PaginationResponse(
            currentCount = courseProcessedData.size,
            currentPage = page,
            totalPage = Math.ceil((courseCount / size).toDouble()).toInt(),
            totalCount = courseCount,
            data = courseProcessedData
        )
        return ResponseEntity(response, HttpStatus.OK);
    }

    @Operation(
        description = "Get course by course id",
        summary = "Get course by course id",
        responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200"
            )
        ],
        deprecated = false
    )
    @GetMapping("/{id}")
    fun getCourse(@PathVariable id: String): ResponseEntity<Any> {
        val courseId = id.toLongOrNull();
        if (courseId === null) {
            return ResponseEntity("Course ID is not a number", HttpStatus.BAD_REQUEST);
        }

        val course = courseService.getCourseById(courseId);

        if (course.isEmpty) {
            return ResponseEntity("Course does not exist", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity(course, HttpStatus.OK);
    }

    @PostMapping(consumes = ["application/json"])
    @Hidden
    fun createCourse(@Valid @RequestBody request: CreateCourseRequest): ResponseEntity<CourseDto> {
        val course = courseService.createCourse(request);
        return ResponseEntity(CourseDto(
            courseId = course.id,
            courseName = course.name,
            courseDescription = course.description,
            courseCategory = course.category,
            active = course.active
        ), HttpStatus.CREATED)
    }

    @PutMapping("/{id}", consumes = ["application/json"])
    @Hidden
    fun updateStudent(@PathVariable id: String, @Valid @RequestBody request: UpdateCourseRequest): ResponseEntity<Any> {
        val courseId = id.toLongOrNull();
        if (courseId === null) {
            return ResponseEntity("Course ID is not a number", HttpStatus.BAD_REQUEST);
        }

        val course = courseService.updateCourse(courseId, request);

        if (course === null) {
            return ResponseEntity("Course not found", HttpStatus.NOT_FOUND);
        } else {
            val studentDto = CourseDto(
                courseId = course.id,
                courseName = course.name,
                courseDescription = course.description,
                courseCategory = course.category,
                active = course.active
            )
            return ResponseEntity(studentDto, HttpStatus.OK)
        }
    }

    // Do not allow DELETE, just use update api to inactive state

    // For Exception handling
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(
        MethodArgumentNotValidException::class
    )
    fun handleValidationExceptions(
        ex: MethodArgumentNotValidException
    ): Map<String, String?> {
        val errors: MutableMap<String, String?> = HashMap()
        ex.bindingResult.allErrors.forEach(Consumer { error: ObjectError ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.getDefaultMessage()
            errors[fieldName] = errorMessage
        })
        return errors
    }
}