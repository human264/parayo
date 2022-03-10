package com.example.parayo.signup


import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.parayo.api.ParayoApi
import com.example.parayo.api.request.SignupRequest
import com.example.parayo.response.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class SignupViewModel(app: Application) : AndroidViewModel(app) {

    val email = MutableLiveData("")
    val name = MutableLiveData("")
    val password = MutableLiveData("")

    suspend fun signup() {

        val request = SignupRequest(email.value, password.value, name.value)
        if (isNotValidSignup(request))
            return

        try {
            val response = requestSignup(request)
            onSignupResponse(response)
        } catch (e: Exception) {
            Log.e(TAG, "signup error")
        }
    }

    private fun isNotValidSignup(signupRequest: SignupRequest) =
        when {
            signupRequest.isNotValidEmail() -> {
                true
            }
            signupRequest.isNotValidPassword() -> {
                true
            }
            signupRequest.isNotValidName() -> {
                true
            }
            else -> false
        }

    private suspend fun requestSignup(request: SignupRequest) =
        withContext(Dispatchers.IO) {
            ParayoApi.instance.signup(request)
        }

    private fun onSignupResponse(response: ApiResponse<Void>) {
        if (response.success) {

        } else {

        }
    }


}