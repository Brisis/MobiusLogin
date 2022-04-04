package com.itinordic.mobiuslogin.retrofit

import com.itinordic.mobiuslogin.data.AccessToken
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

   // Dead Code
//    companion object {
//        operator fun invoke(): GetDataService {
//            val requestIntercepter = Interceptor { chain ->
//                val url = chain.request()
//                    .url()
//                    .newBuilder()
//                    .addQueryParameter("key", API_KEY)
//                    .build()
//
//                val request = chain.request()
//                    .newBuilder()
//                    .url(url)
//                    .build()
//
//                return@Interceptor chain.proceed(request)
//            }
//
//            val okHttpClient = OkHttpClient.Builder().addInterceptor(requestIntercepter).build()
//
//            val baseUrl = "http://10.0.2.2:8080"
//
//            return Retrofit.Builder()
//                .client(okHttpClient)
//                .baseUrl(baseUrl)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//                .create(GetDataService::class.java)
//
//        }
//    }
}