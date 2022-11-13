package com.example.bindimapstest

import com.example.bindimaps.Position

data class VenueDwellInfoData(
    val venue: String,
    val position: Position,
    val endTimeUtc: String,
    val startTimeLocal: String,
    val startTimeUtc: String,
    val userTimeUtc: String,
    val dwellTimeUtc: String,
)