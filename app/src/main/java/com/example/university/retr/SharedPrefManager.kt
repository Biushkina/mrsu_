package com.example.university.retr

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


object SharedPrefManager {

    private const val PREF_NAME = "MyPrefs"
    private const val ACCESS_TOKEN = "access_token"
    private const val REFRESH_TOKEN = "refresh_token"
    private const val STUDENT_DATA = "student_data"
    private const val USER_DATA = "user_data"
    private const val USER_EDUCATION = "user_education"

    private const val STUDENT_SEMESTER = "student_semester"
    private const val STUDENT_TIMETABLE = "student_timetable"
    private const val STUDENT_RATINGPLAN = "student_ratingplan"
    private const val EXPIRATION_TIME = "expiration_time"

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var instance: SharedPrefManager

    fun getInstance(context: Context): SharedPrefManager {
        if (!this::instance.isInitialized) {
            instance = SharedPrefManager
            init(context)
        }
        return instance
    }

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val refreshToken = REFRESH_TOKEN
        if (refreshToken != null) {
            refreshDataUsingRefreshToken()
        }

    }

    fun refreshDataUsingRefreshToken() {
        val BASE_URL_USER = "https://papi.mrsu.ru"

        val userApi = createRetrofitApi(BASE_URL_USER)


        CoroutineScope(Dispatchers.IO).launch {
            try {
                checkTokenExpiration()       //проверяем истечение времени использования токена

                val currentAccessToken = getAccessToken()        //получаем токен


                val refreshedUserData = userApi.getUser("Bearer ${currentAccessToken}")     //обновляем данные пользователя
                saveUserData(refreshedUserData)

                val refreshedEducation = userApi.getUserEducation("Bearer ${currentAccessToken}")  //обновлем образование студента
                saveUserEducation(refreshedEducation)

                /*val refreshedStudentData = userApi.getStudent("Bearer ${currentAccessToken}")    //обновляем данные студента
                saveStudentData(refreshedStudentData)*/

                /*Обновление расписания студента
                val studentTimeTable = userApi.getStudentTimeTable("Bearer ${currentAccessToken}", SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()))
                saveStudentTimeTable(studentTimeTable)*/

            } catch (e: Exception) {

            }
        }

    }

    fun getAccessToken(): String? {
        return sharedPreferences.getString(ACCESS_TOKEN,null)
    }

    fun checkTokenExpiration() {
        if (isAccessTokenExpired())
        {
            val BASE_URL_TOKEN = "https://p.mrsu.ru"
            val tokenApi = createRetrofitApi(BASE_URL_TOKEN)

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val userToken = tokenApi.getNewToken(refreshToken = getRefreshToken().toString())
                    saveToken(userToken)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
    fun getRefreshToken(): String? {
        return sharedPreferences.getString(REFRESH_TOKEN, null)
    }
    fun saveToken(userToken: Token) {
        sharedPreferences.edit().apply {
            putString(ACCESS_TOKEN, userToken.accessToken)
            putString(REFRESH_TOKEN, userToken.refreshToken)
            putLong(EXPIRATION_TIME, userToken.expiresIn * 1000 + System.currentTimeMillis() + 150)
            apply()
        }
    }



    fun createRetrofitApi(baseUrl: String): api {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(api::class.java)

    }

    //Проверка на истечение времени использования токена
    fun isAccessTokenExpired(): Boolean {
        val currentTime = System.currentTimeMillis()

        return currentTime >= getExpTime()
    }
    fun getExpTime(): Long {
        return sharedPreferences.getLong(EXPIRATION_TIME, 0)
    }

    fun getUserData(): User? {
        val jsonUserData = sharedPreferences.getString(USER_DATA, null)
        return Gson().fromJson(jsonUserData, User::class.java)
    }

    fun getUserEducation(): UserEducation? {
        val jsonUserData = sharedPreferences.getString(USER_EDUCATION, null)
        return Gson().fromJson(jsonUserData, UserEducation::class.java)
    }

    fun saveUserData(userData: User) {
        val jsonUserData = Gson().toJson(userData)
        sharedPreferences.edit().apply {
            putString(USER_DATA, jsonUserData)
            apply()
        }
    }
    fun saveUserEducation(userEducation: UserEducation) {
        val jsonUserData = Gson().toJson(userEducation)
        sharedPreferences.edit().apply {
            putString(USER_EDUCATION, jsonUserData)
            apply()
        }
    }
    /*fun saveStudentData(studentData: Student) {
        val jsonStudentData = Gson().toJson(studentData)
        sharedPreferences.edit().apply {
            putString(STUDENT_DATA, jsonStudentData)
            apply()
        }
    }*/
    fun saveStudentTimeTable(studentTimeTable: List<StudentTimeTable>) {
        val jsonStudentTimeTable = Gson().toJson(studentTimeTable)
        sharedPreferences.edit().apply {
            putString(STUDENT_TIMETABLE, jsonStudentTimeTable)
            apply()
        }
    }
}