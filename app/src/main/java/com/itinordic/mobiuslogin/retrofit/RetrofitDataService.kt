package com.itinordic.mobiuslogin.retrofit

data class RetrofitDataService(
    val client_id: String,
    val grant_type: String,
    val client_secret: String,
    val scope: String,
    val username: String,
    val password: String,
)
