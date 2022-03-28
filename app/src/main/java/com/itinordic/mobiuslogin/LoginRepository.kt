package com.itinordic.mobiuslogin

interface LoginRepository {
    fun login(username: String, password: String,) : Boolean
}