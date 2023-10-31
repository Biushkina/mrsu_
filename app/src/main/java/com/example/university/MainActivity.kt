package com.example.university

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.university.retr.api
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userLogin: EditText = findViewById(R.id.login)
        val userPass: EditText = findViewById(R.id.parol)
        val button: Button = findViewById(R.id.enter_button)

        val linkToLk: Button = findViewById(R.id.enter_button)

        val b:Button = findViewById(R.id.testt)
        val test_FIo:TextView = findViewById(R.id.test_FIO)
        val test_Id:TextView = findViewById(R.id.test_Id)

        val token = "Bearer Zwm6trqb_HyCPxTxEyqaVwYf9ZsBYL1wem3x2xUj56wRUP8BeaR03NKzYwoI0Cjk1mgMbJ5w82puV-8VNN83O2qIlC3CvfQYW0FIs0JYIGfZN39cYJHvW-xsMOY6INY0N_oatHBRvOELRc6Z2rXGIu-NDpC3gF1r6Vxk9S7DshBJdnCGYD1En0gKxXTleSr3D6p_QRdMC_NVn0w7Ea5jElakTL5NnoMK_tqsZ1EhMr-NNnACSFL9rL9atxF5xcMbAPlq5qrIZEJSW5-XBVxBnvmQ2_6neP6VCn9eioGz890o0VMeO7H_Lvk2MTso9zOO7QTslgGWh-cR8gAhu-DVDkZYyIO9PM01kLOHZUfBZvbY5VslyU7tUJqgPXbPfZG89dspo3HKOcmBiNm4XwDMOSD2z9Qh2igALNle4uxFIvlkigx_Hee6daWp7Txq2gFfU2aEM26rjda2gaQSvwpwSBsRbt8a5LW_wInEfbQoaUwmnj1XQd9nOpDy-BaQvx7vZW6ddtYP9jlfW329JnMwFM0RXAgYwK-g04I0qEFTIBBa9KsG"
        val retrofit = Retrofit.Builder()
            .baseUrl("https://papi.mrsu.ru")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val mainApi = retrofit.create(api::class.java)

        b.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val user = mainApi.getUser(token)
                runOnUiThread {

                    test_FIo.text = user.FIO
                    test_Id.text = user.StudentCod

                }
            }
        }
        button.setOnClickListener {

            val login = userLogin.text.toString().trim()
            val pass = userPass.text.toString().trim()

            if(login == "" || pass == "")
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show()
            else {
                val intent = Intent(this, bottom_menu::class.java)
                startActivity(intent)
            }
        }


    }
}