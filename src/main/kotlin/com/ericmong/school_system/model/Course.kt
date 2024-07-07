package com.ericmong.school_system.model

import jakarta.persistence.*

@Entity
@Table(name="courses")
data class Course(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long = 0,
    val name: String,
    val description: String,
    val category: String, // Math, Science, Language, Programming, Liberal Arts...
    val active: Boolean,
    val remarks: String, // Private remarks regarding courses, to be viewed only internally by teachers or admins
)
