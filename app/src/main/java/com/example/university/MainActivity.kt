package com.example.university

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.university.retr.SharedPrefManager
import com.example.university.retr.Token
import com.example.university.retr.api
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

class MainActivity : AppCompatActivity() {

    private val BASE_URL_TOKEN = "https://p.mrsu.ru"
    private val BASE_URL_USER = "https://papi.mrsu.ru"
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPrefManager = SharedPrefManager.getInstance(this)

        val userLogin: EditText = findViewById(R.id.login)
        val userPass: EditText = findViewById(R.id.parol)
        val button: Button = findViewById(R.id.enter_button)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        //val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        //Получение API
        val tokenApi = createRetrofitClient(BASE_URL_TOKEN).create(api::class.java)
        val userApi = createRetrofitClient(BASE_URL_USER).create(api::class.java)

        button.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {

            try {
                val userToken = tokenApi.getToken(
                    username = userLogin.text.toString(),
                    password = userPass.text.toString()
                )
                handleTokenResponse(userToken, sharedPrefManager, userApi)
            } catch (e: Exception) {
                authorization_handler(e)
                Log.d("getUserToken_error", e.message.toString())
            }
            }
        }
    }

    private fun authorization_handler(throwable: Throwable) {
        Log.e("error_global", throwable.message.toString())
        Log.e("error_local", throwable.localizedMessage)
        runOnUiThread {
            showErrorToast("Ошибка авторизации")
        }
    }

    private fun handleTokenResponse(userToken: Token, sharedPrefManager: SharedPrefManager, userApi: api) {
        if (userToken.accessToken != null) {
            sharedPrefManager.saveToken(userToken)
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            CoroutineScope(Dispatchers.IO).launch {
                try {

                    //Получение информации о пользователе
                    val user = userApi.getUser("Bearer ${userToken.accessToken}")
                    sharedPrefManager.saveUserData(user)

                    val userEducat = userApi.getUserEducation("Bearer ${userToken.accessToken}")
                    sharedPrefManager.saveUserEducation(userEducat)

                    //Получение информации о студенте
                    /*val student = userApi.getStudent("Bearer ${userToken.accessToken}")
                    sharedPrefManager.saveStudentData(student)*/

                    /*Получение информации о дисциплинах студента
                    val studentsemester = userApi.getStudentSemester("Bearer ${userToken.accessToken}")
                    sharedPrefManager.saveStudentSemester(studentsemester)
                    */
                    //Получение информации о расписании студента
                    //val studenttimetable = userApi.getStudentTimeTable("Bearer ${userToken.accessToken}", SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()))
                    //sharedPrefManager.saveStudentTimeTable(studenttimetable)

                    runOnUiThread {
                        performActionsAfterAuthentication()
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        showErrorToast("Ошибка при получении пользовательских данных в handleTokenResponse ")
                        Log.e("error_global", e.message.toString())
                        Log.e("error_local", e.localizedMessage)
                    }
                }
            }
        } else {
            runOnUiThread {
                showErrorToast("Ошибка при авторизации")
            }
        }
    }

    //Переход между окнами
    private fun performActionsAfterAuthentication() {
        val intent = Intent(this@MainActivity, bottom_menu::class.java)
        startActivity(intent)
    }

    //Создание клиента
    private fun createRetrofitClient(baseUrl: String): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private fun showErrorToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}