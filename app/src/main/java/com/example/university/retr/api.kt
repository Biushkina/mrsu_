package com.example.university.retr

import android.util.JsonToken
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface api {

    @Headers(
        "Content-Type: application/json"
    )
    @GET("v1/User")
    suspend fun getUser(@Header("Autorization")token: String):User
}