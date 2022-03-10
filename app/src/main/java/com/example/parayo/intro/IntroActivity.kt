package com.example.parayo.intro


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.parayo.R
import com.example.parayo.SigninActivity
import com.example.parayo.api.ParayoApi
import com.example.parayo.signup.SignupActivity
import kotlinx.coroutines.*
import java.lang.Exception

class IntroActivity: AppCompatActivity()  {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.intro_activity)
        val intent:Intent = Intent(this, SigninActivity::class.java)

        GlobalScope.launch(Dispatchers.Main) {
            delay(1000)
            startActivity(intent)
        }



    }
}
