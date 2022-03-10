package com.example.parayo

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.parayo.databinding.ActivitySigninBinding
import com.example.parayo.signin.SigninViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.security.AccessController.getContext

class SigninActivity : AppCompatActivity() {

    private lateinit var viewModel: SigninViewModel
    private lateinit var binding: ActivitySigninBinding

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[SigninViewModel::class.java]

        viewModel.email.observe(this, Observer {
            Log.d(TAG, "Main Activity email: $it")
            binding.editTextTextEmailAddress.setText(it.toString())
        })

        viewModel.password.observe(this, Observer {
            Log.d(TAG, "Main Activity password: $it")
            binding.editTextTextPassword.setText(it.toString())
        })


        binding.signInButton.setOnClickListener {
            val userInputEmail = binding.editTextTextEmailAddress.text
            val userInputPassword = binding.editTextTextPassword.text
            viewModel.email.value = userInputEmail.toString()
            viewModel.password.value = userInputPassword.toString()

            GlobalScope.launch(Dispatchers.Main) {
                viewModel.signin()

                runOnUiThread {
                    viewModel.message.observe(this@SigninActivity, Observer {
                        it.getContentIfNotHandled()?.let {
                            Toast.makeText(this@SigninActivity, it, Toast.LENGTH_LONG).show()

                        }
                    })
                }
            }


        }


    }
}