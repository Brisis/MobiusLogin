package com.itinordic.mobiuslogin

import android.util.Log
import kotlinx.coroutines.delay

/**
 * I will put mobius service in here
 */
class LoginService : LoginRepository {
    override fun login(username: String, password: String,): Boolean {
        // I
//        val retrofitApi : GetDataService = RetrofitAPI.getInstance().create(GetDataService::class.java)
//        var isSuccess = false
//        // launching a new coroutine
//
//        val result : Call<AccessToken> = retrofitApi.getAccessToken(
//            client_id = "Login",
//            grant_type = "password",
//            client_secret = "HdCwTCNCFG0vEu68LbhHTrlyISJ8QCSR",
//            scope = "openid",
//            username = username,
//            password = password,
//        )
//
//        Log.i("InputItems", "Email: $username, Password: $password,")
//
//        result.enqueue(object: Callback<AccessToken> {
//            override fun onResponse(
//                call: Call<AccessToken>,
//                response: Response<AccessToken>
//            ) {
//                if (response.isSuccessful){
//                    Log.d("AccessToken: ", response.body().toString())
//                    isSuccess = true
//                }
//                else {
//                    Log.d("AccessTokenError: ", response.errorBody().toString())
//                    isSuccess = false
//                    //Toast.makeText((requireActivity() as MainActivity),"An error occurred!", Toast.LENGTH_LONG).show()
//                }
//            }
//
//            override fun onFailure(call: Call<AccessToken>, t: Throwable) {
//                Log.d("AccessError: ", t.message.toString())
//                isSuccess = false
//            }
//
//        })

        Log.v("User Input: ", "Email: $username, Password: $password ")


        return true
    }
}