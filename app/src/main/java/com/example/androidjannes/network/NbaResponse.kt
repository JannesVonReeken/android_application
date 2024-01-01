package com.example.androidjannes.network

data class NbaResponse(
    val get: String,
    val parameters: List<String>,
    val errors: List<String>,
    val results: Int,
    val response: List<Int>
)