package com.ericmong.school_system.dto

data class PaginationResponse<T>(
    val currentPage: Int,
    val currentCount: Int,
    val totalPage: Int,
    val totalCount: Int,
    val data: List<T>
)
