package com.itinordic.mobiuslogin.data

import com.google.gson.annotations.SerializedName

class AccessToken{
    @SerializedName("access_token")
    private val accessToken: String
    @SerializedName("expires_in")
    private val expiresIn: Int
    @SerializedName("refresh_expires_in")
    private val refreshExpiresIn: Int
    @SerializedName("refresh_token")
    private val refreshToken: String
    @SerializedName("token_type")
    private val tokenType: String
    @SerializedName("id_token")
    private val idToken: String
    @SerializedName("not-before-policy")
    private val notBeforePolicy: Int
    @SerializedName("session_state")
    private val sessionState: String
    @SerializedName("scope")
    private val scope: String

    constructor(
        accessToken: String,
        expiresIn: Int,
        refreshExpiresIn: Int,
        refreshToken: String,
        tokenType: String,
        idToken: String,
        notBeforePolicy: Int,
        sessionState: String,
        scope: String
    ) {
        this.accessToken = accessToken
        this.expiresIn = expiresIn
        this.refreshExpiresIn = refreshExpiresIn
        this.refreshToken = refreshToken
        this.tokenType = tokenType
        this.idToken = idToken
        this.notBeforePolicy = notBeforePolicy
        this.sessionState = sessionState
        this.scope = scope
    }
}