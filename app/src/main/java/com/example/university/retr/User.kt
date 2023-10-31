package com.example.university.retr

data class User(
    val Email: String,
    val EmailConfirmed: Boolean,
    val EnglishFIO: String,
    val TeacherCod: String,
    val StudentCod: String,
    val BirthDate: String,
    val AcademicDegree: String,
    val AcademicRank: String,
    val Roles: List<Role>,
    val Id: String,
    val UserName: String,
    val FIO: String,
    val Photo: Photo
)

data class Role(
    val Name: String,
    val Description: String
)

data class Photo(
    val UrlSmall: String,
    val UrlMedium: String,
    val UrlSource: String
)