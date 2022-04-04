package com.itinordic.mobiuslogin.data

import com.google.gson.annotations.SerializedName

class AccessToken(
    @SerializedName("access_token") internal val accessToken: String, @SerializedName("expires_in")
    private val expiresIn: Int, @SerializedName("refresh_expires_in")
    private val refreshExpiresIn: Int, @SerializedName("refresh_token")
    private val refreshToken: String, @SerializedName("token_type")
    private val tokenType: String, @SerializedName("id_token")
    private val idToken: String, @SerializedName("not-before-policy")
    private val notBeforePolicy: Int, @SerializedName("session_state")
    private val sessionState: String, @SerializedName("scope")
    private val scope: String
) {

}