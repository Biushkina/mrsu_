package com.example.university.retr

import java.util.Date

data class UserEducation(
    val type: String,
    val level: Boolean,
    val Institution: String,
    val Specialty: String,
    val Qualification: String,
    val DocumentTitle: String,
    val DocumentSeries: String,
    val DocumentNumber: String,
    val DocumentDateOfIssue: Date,
)