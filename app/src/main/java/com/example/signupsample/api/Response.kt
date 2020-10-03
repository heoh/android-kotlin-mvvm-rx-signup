package com.example.signupsample.api

data class Response<T>(
    val statusCode: Int,
    val body: T? = null,
    val message: CharSequence? = null
)