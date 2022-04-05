package com.itinordic.mobiuslogin.mobius

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.itinordic.mobiuslogin.LoginFragment
import com.itinordic.mobiuslogin.MainActivity
import com.itinordic.mobiuslogin.R
import com.itinordic.mobiuslogin.data.AccessToken
import com.itinordic.mobiuslogin.mobius.effects.AttemptLogin
import com.itinordic.mobiuslogin.mobius.effects.Effect
import com.itinordic.mobiuslogin.mobius.effects.NavigateToProfile
import com.itinordic.mobiuslogin.mobius.effects.ShowErrorMessage
import com.itinordic.mobiuslogin.mobius.events.Event
import com.itinordic.mobiuslogin.mobius.events.LoginFailed
import com.itinordic.mobiuslogin.mobius.events.LoginSuccessful
import com.itinordic.mobiuslogin.retrofit.LoginService
import com.spotify.mobius.Connectable
import com.spotify.mobius.Connection
import com.spotify.mobius.functions.Consumer
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Response
import java.util.*
import kotlin.text.StringBuilder

class LoginEffectHandler : Connectable<Effect, Event> {
lateinit var responseToken: String
    override fun connect(output: Consumer<Event>): Connection<Effect> {
        return object: Connection<Effect>{
            override fun accept(effect: Effect) {
                when(effect){
                    is AttemptLogin -> {
                        Log.i("Message: ", "Attempting Login")
                        Log.d("Message", "Username: ${effect.email}, Password: ${effect.password}")

                        GlobalScope.launch {
                            val response = LoginService().login(effect.email, effect.password)
                            responseToken = response.body()!!.accessToken

                            if (response.isSuccessful)
                                output.accept(LoginSuccessful)
                            else
                                output.accept(LoginFailed("Username or password incorrect "))

                        }
                    }

                    is NavigateToProfile -> {
                        //Get Access Token
                        Log.i("Message", "Access Token : $responseToken")

                        //Navigate Here
                        Log.i("Message", "We are now heading to Profile")
                    }

                    is ShowErrorMessage -> {
                        Log.i("Message: ", effect.msg)
                    }
                }
            }

            override fun dispose() {}
        }
    }
}