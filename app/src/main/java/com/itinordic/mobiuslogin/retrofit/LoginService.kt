package com.itinordic.mobiuslogin.retrofit

import android.util.Log
import com.itinordic.mobiuslogin.LoginFragment
import com.itinordic.mobiuslogin.data.AccessToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * I will put mobius service in here
 */
class LoginService {
    lateinit var responseToken: String
    fun login(username: String,password: String) : String {
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
                if (response.isSuccessful){
                    responseToken = response.body()!!.accessToken
                    Log.d("AccessToken: ", responseToken)
                    LoginFragment.newInstance(responseToken)
                }
                else {
                    Log.d("AccessTokenError: ", responseToken)
                }
            }

            override fun onFailure(call: Call<AccessToken>, t: Throwable) {
                Log.d("AccessError: ", t.message.toString())
            }

        })

        return responseToken
    }
}