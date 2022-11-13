package com.example.bindimaps

data class SessionsItem(
    val endTimeUtc: String,
    val startTimeLocal: String,
    val path: List<Path>,
    val sessionId: String,
    val startTimeUtc: String,
    val userId: String
)