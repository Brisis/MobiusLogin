package com.itinordic.mobiuslogin.retrofit

import com.itinordic.mobiuslogin.data.AccessToken
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface GetDataService {
    @FormUrlEncoded
    @POST("/realms/demo/protocol/openid-connect/token")
    fun getAccessToken(
        @Field("client_id") client_id: String,
        @Field("grant_type") grant_type: String,
        @Field("client_secret") client_secret: String,
        @Field("scope") scope: String,
        @Field("username") username: String,
        @Field("password") password: String,
    ) : Call<AccessToken>
}