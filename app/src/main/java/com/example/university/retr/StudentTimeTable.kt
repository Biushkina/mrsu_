package com.example.university.retr

import com.google.gson.annotations.SerializedName

data class StudentTimeTable(
    @SerializedName("Group")
    val group: String,
    @SerializedName("PlanNumber")
    val planNumber: String,
    @SerializedName("FacultyName")
    val facultyName: String,
    @SerializedName("TimeTableBlockd")
    val timeTableBlockd: Int,
    @SerializedName("TimeTable")
    val timeTable: TimeTable
)
data class TimeTable(
    @SerializedName("Date")
    val date: String,
    @SerializedName("Lessons")
    val lessons: List<TimeTableLesson>
)

data class TimeTableLesson(
    @SerializedName("Number")
    val number: Byte,
    @SerializedName("SubgroupCount")
    val subgroupCount: Byte,
    @SerializedName("Disciplines")
    val disciplines: List<TimeTableLessonDiscipline>
)

data class TimeTableLessonDiscipline(
    @SerializedName("Id")
    val id: Int,
    @SerializedName("Title")
    val title: String,
    @SerializedName("Language")
    val language: String,
    @SerializedName("LessonType")
    val lessonType: TimeTableLessonDisciplineType,
    @SerializedName("Remote")
    val remote: Boolean,
    @SerializedName("Group")
    val group: String,
    @SerializedName("SubgroupNumber")
    val subgroupNumber: Byte,
    @SerializedName("Teacher")
    val teacher: UserCrop,
    @SerializedName("Auditorium")
    val auditorium: Auditorium
)

enum class TimeTableLessonDisciplineType {
    @SerializedName("Default")
    DEFAULT,

    @SerializedName("Consultation")
    CONSULTATION,

    @SerializedName("Offset")
    OFFSET,

    @SerializedName("Exam")
    EXAM
}

data class UserCrop(
    @SerializedName("Id")
    val id: String,
    @SerializedName("UserName")
    val name: String,
    @SerializedName("FIO")
    val fio: String,
    //@SerializedName("Photo")
    //val photo: UserPhoto
)

data class Auditorium(
    @SerializedName("Id")
    val id: Int,
    @SerializedName("Number")
    val number: String,
    @SerializedName("Title")
    val title: String,
    @SerializedName("CampusId")
    val campusid: Int,
    @SerializedName("CampusTitle")
    val campustitle: String
)

