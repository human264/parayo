package com.example.parayo.signin

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.parayo.api.ParayoApi
import com.example.parayo.common.Event
import com.example.parayo.common.Prefs
import com.example.parayo.request.SigninRequest
import com.example.parayo.response.ApiResponse
import com.example.parayo.response.SigninResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SigninViewModel() : ViewModel() {

    var email = MutableLiveData("")
    val password = MutableLiveData("")

    private val statusMessage = MutableLiveData<Event<String>>()

    val message: LiveData<Event<String>>
    get() = statusMessage

    suspend fun signin() {
        val reqeust = SigninRequest(email.value, password.value)
        if (isNotValidSignin(reqeust))
            return

        try {
            val response = requestSignin(reqeust)
            onSigninResponse(response)
        } catch (e: Exception) {
            val msg = "알 수 없는 오류가 발생하였습니다."
            Log.e(TAG, msg)
        }
    }

    private fun isNotValidSignin(request: SigninRequest) =
        when {
            request.isNotValidEmail() -> {
                val msg = "이메일 형식이 정확하지 않습니다."
                Log.e(TAG, msg)
                statusMessage.value = Event(msg)
                true
            }
            request.isNotValidPassword() -> {
                val msg = "비밀번호는 8자 이상 20자 이하로 입력해주세요."
                Log.e(TAG, msg)
                statusMessage.value = Event(msg)
                true
            }
            else -> false
        }

    private suspend fun requestSignin(request: SigninRequest) =
        withContext(Dispatchers.IO) {
            ParayoApi.instance.signin(request)
        }

    private fun onSigninResponse(response: ApiResponse<SigninResponse>) {
        if (response.success && response.data != null) {
            Prefs.token = response.data.token
            Prefs.refreshToken = response.data.userName
            Prefs.userId = response.data.userId

            val msg = "로그인이 되었습니다."
            Log.d(TAG, msg)
            statusMessage.value = Event(msg)
        } else {

            val msg = "알 수 없는 오류가 발생 하였습니다."
            Log.d(TAG, msg )
            statusMessage.value = Event(msg)
        }
    }




}