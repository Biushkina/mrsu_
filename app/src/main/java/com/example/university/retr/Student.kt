package com.example.university.retr

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Student(
    @SerializedName("Id")
    val id: String,
    @SerializedName("Email")
    val email: String,
    @SerializedName("EmailConfirmed")
    val emailConfirmed: Boolean,
    @SerializedName("FIO")
    val fio: String,
    @SerializedName("EnglishFIO")
    val englishFIO: String,
    @SerializedName("StudentCod")
    val studentCod: String,
    @SerializedName("BirthDate")
    val birthDate: String,
    @SerializedName("SNILS")
    val snils: String,
    @SerializedName("Foreigner")
    val foreigner: Boolean,
    @SerializedName("Citizenship")
    val citizenship: String,
    @SerializedName("Sex")
    val sex: String,
    @SerializedName("Address")
    val address: String,
    @SerializedName("Contacts")
    val contact: String,
    @SerializedName("RecordBooks")
    val recordBooks: List<RecordBooks>
)

data class StudentSemester(
    @SerializedName("RecordBooks")
    val recordBooks: List<RecordBooks_StudentSemester>,
    @SerializedName("UnreadedDisCount")
    val unreadedDisCount: Int,
    @SerializedName("UnreadedDisMesCount")
    val unreadedDisMesCount: Int,
    @SerializedName("Year")
    val year: String,
    @SerializedName("Period")
    val period: Int,
)
data class RecordBooks_StudentSemester(
    @SerializedName("Cod")
    val cod: String,
    @SerializedName("Number")
    val number: String,
    @SerializedName("Faculty")
    val faculty: String,
    @SerializedName("Disciplines")
    val discipline: List<Discipline>
)

data class Discipline(
    @SerializedName("Relevance")
    val relevance: Boolean,
    @SerializedName("IsTeacher")
    val isTeacher: Boolean,
    @SerializedName("UnreadedCount")
    val unreadedCount: Int,
    @SerializedName("UnreadedMessageCount")
    val unreadedMessageCount: Int,
    @SerializedName("Groups")
    val groups: List<String>,
    @SerializedName("DocFiles")
    val docFiles: List<List<DocFiles>>,
    @SerializedName("WorkingProgramm")
    val workingProgramm: List<DocFiles>,
    @SerializedName("Id")
    val id: Int,
    @SerializedName("PlanNumber")
    val planNumber: String,
    @SerializedName("Year")
    val year: String,
    @SerializedName("Faculty")
    val faculty: String,
    @SerializedName("EducationForm")
    val educationForm: String,
    @SerializedName("EducationLevel")
    val educationLevel: String,
    @SerializedName("Speciality")
    val speciality: String,
    @SerializedName("SpecialityCod")
    val specialityCod: String,
    @SerializedName("Profile")
    val profile: String,
    @SerializedName("PeriodString")
    val periodString: String,
    @SerializedName("PeriodInt")
    val periodInt: Int,
    @SerializedName("Title")
    val title: String,
    @SerializedName("Language")
    val language: String
)
data class DocFiles(
    @SerializedName("Id")
    val id: String,
    @SerializedName("CreatorId")
    val creatorId: String,
    @SerializedName("Title")
    val title: String,
    @SerializedName("FileName")
    val fileName: String,
    @SerializedName("MIMEtype")
    val mimetype: String,
    @SerializedName("Size")
    val size: Int,
    @SerializedName("Date")
    val date: Date,
    @SerializedName("URL")
    val url: String
)