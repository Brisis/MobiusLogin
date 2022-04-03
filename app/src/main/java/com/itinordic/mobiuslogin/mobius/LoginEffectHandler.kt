package com.itinordic.mobiuslogin.mobius

import android.util.Log
import com.itinordic.mobiuslogin.mobius.effects.AttemptLogin
import com.itinordic.mobiuslogin.mobius.effects.Effect
import com.itinordic.mobiuslogin.mobius.effects.NavigateToProfile
import com.itinordic.mobiuslogin.mobius.effects.ShowErrorMessage
import com.itinordic.mobiuslogin.mobius.events.Event
import com.spotify.mobius.Connectable
import com.spotify.mobius.Connection
import com.spotify.mobius.functions.Consumer

class LoginEffectHandler : Connectable<Effect, Event> {
    override fun connect(output: Consumer<Event>): Connection<Effect> {
        return object: Connection<Effect>{
            override fun accept(effect: Effect) {
                when(effect){
                    is AttemptLogin -> {
                        Log.i("Message: ", "Attempting Login")
                    }

                    is NavigateToProfile -> {
                        Log.i("Message: ", "We are now heading to Profile")
                    }

                    is ShowErrorMessage -> {
                        Log.i("Message: ", "This is an error message from Effect Handler")
                    }
                }
            }

            override fun dispose() {}
        }
    }
}