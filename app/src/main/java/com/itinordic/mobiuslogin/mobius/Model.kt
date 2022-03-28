package com.itinordic.mobiuslogin.mobius

data class Model(
    val email: String,
    val password : String,
    val canLogin : Boolean,
    val loggingIn : Boolean,
)
