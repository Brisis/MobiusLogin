package com.itinordic.mobiuslogin.mobius

import android.util.Log
import com.itinordic.mobiuslogin.data.AccessToken
import com.itinordic.mobiuslogin.mobius.effects.AttemptLogin
import com.itinordic.mobiuslogin.mobius.effects.Effect
import com.itinordic.mobiuslogin.mobius.effects.NavigateToProfile
import com.itinordic.mobiuslogin.mobius.effects.ShowErrorMessage
import com.itinordic.mobiuslogin.mobius.events.Event
import com.itinordic.mobiuslogin.retrofit.LoginService
import com.spotify.mobius.Connectable
import com.spotify.mobius.Connection
import com.spotify.mobius.functions.Consumer
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Response
import java.util.*
import kotlin.text.StringBuilder

//class LoginEffectHandler : ObservableTransformer<Effect, Event> {
//    override fun apply(effect: Observable<Effect>): ObservableSource<Event> {
//        return effect.ofType(AttemptLogin)
//    }
//}

class LoginEffectHandler : Connectable<Effect, Event> {
    override fun connect(output: Consumer<Event>): Connection<Effect> {
        return object: Connection<Effect>{
            override fun accept(effect: Effect) {
                when(effect){
                    is AttemptLogin -> {
                        Log.i("Message: ", "Attempting Login")
                        Log.d("Message", "Username: ${effect.email}, Password: ${effect.password}")

                        LoginService().login(effect.email, effect.password)
                    }

                    is NavigateToProfile -> {
                        Log.i("Message: ", "We are now heading to Profile")
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