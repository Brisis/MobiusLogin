package com.itinordic.mobiuslogin.retrofit

import android.util.Log
import com.itinordic.mobiuslogin.LoginFragment
import com.itinordic.mobiuslogin.data.AccessToken
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * I will put mobius service in here
 */
class LoginService {
    lateinit var responseToken: String
    lateinit var responseBody: Response<AccessToken>
    var isSuccess : Boolean = false
    suspend fun login(username: String, password: String) : Response<AccessToken> {
        val retrofitApi : GetDataService = RetrofitAPI.getInstance().create(GetDataService::class.java)
        // launching a new coroutine

        val result : Call<AccessToken> = retrofitApi.getAccessToken(
            client_id = "Login",
            grant_type = "password",
            client_secret = "HdCwTCNCFG0vEu68LbhHTrlyISJ8QCSR",
            scope = "openid",
            username = username,
            password = password,
        )

        Log.i("InputItems", "Email: $username, Password: $password,")

        result.enqueue(object: Callback<AccessToken> {
            override fun onResponse(
                call: Call<AccessToken>,
                response: Response<AccessToken>
            ) {
                responseBody = response
                if (response.isSuccessful){
                    Log.d("AccessToken: ", response.message())
                }
                else {
                    Log.d("AccessTokenError: ", response.message())
                }
            }

            override fun onFailure(call: Call<AccessToken>, t: Throwable) {
                Log.d("AccessError: ", t.message.toString())
            }

        })

        delay(2000)

        return responseBody
    }
}