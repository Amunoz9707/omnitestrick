package com.test.omni_test_rick.domain.dto

data class Error(
    val url: String,
    val status: Int,
    val statusText: String,
    val body: ErrorBody,
)

data class ErrorBody(
    val error: String
)