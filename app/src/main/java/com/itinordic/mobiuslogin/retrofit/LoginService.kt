package com.itinordic.mobiuslogin.retrofit

import android.util.Log
import com.itinordic.mobiuslogin.data.AccessToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * I will put mobius service in here
 */
class LoginService {
    fun login(username: String,password: String){
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
                    Log.d("AccessToken: ", response.body().toString())
                }
                else {
                    Log.d("AccessTokenError: ", response.errorBody().toString())
                    //Toast.makeText((requireActivity() as MainActivity),"An error occurred!", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<AccessToken>, t: Throwable) {
                Log.d("AccessError: ", t.message.toString())
            }

        })

    }
}