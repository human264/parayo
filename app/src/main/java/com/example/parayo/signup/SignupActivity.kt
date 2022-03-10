package com.example.parayo.signup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider


import com.example.parayo.R
import com.example.parayo.databinding.ActivitySignupBinding


class SignupActivity : AppCompatActivity() {

    private var viewModel: SignupViewModel? = null
    private var binding: ActivitySignupBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        viewModel = ViewModelProvider(this)[SignupViewModel::class.java]


    }


}