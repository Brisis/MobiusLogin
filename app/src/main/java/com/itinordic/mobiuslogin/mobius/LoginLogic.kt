package com.itinordic.mobiuslogin.mobius

import com.itinordic.mobiuslogin.mobius.effects.AttemptLogin
import com.itinordic.mobiuslogin.mobius.effects.Effect
import com.itinordic.mobiuslogin.mobius.effects.NavigateToProfile
import com.itinordic.mobiuslogin.mobius.effects.ShowErrorMessage
import com.itinordic.mobiuslogin.mobius.events.*
import com.spotify.mobius.Effects
import com.spotify.mobius.Next
import com.spotify.mobius.Update
import java.lang.IllegalStateException

class LoginLogic : Update<Model, Event, Effect> {
    override fun update(model: Model, event: Event): Next<Model, Effect> {
        when(event){
            is EmailInputChanged -> {
                return onEmailChanged(model, event)
            }
            is PasswordInputChanged -> {
                return onPasswordChanged(model, event)
            }
            is LoginRequested -> {
                return onLoginRequested(model)
            }
            is LoginSuccessful -> {
                return onLoginSuccess(model)
            }
            is LoginFailed -> {
                return onLoginFailed(model, event)
            }
            else -> {
                throw IllegalStateException("Unhandled event: $event")
            }
        }
    }

    private fun verifyCreds(email: String, password: String) : Boolean {
        return email.isNotEmpty() && password.isNotEmpty()
    }

    private fun onEmailChanged(model: Model, event: EmailInputChanged) : Next<Model, Effect> =
        Next.next(
            model.copy(
                email = event.email,
                canLogin = verifyCreds(event.email, model.password)
            ),

            )

    private fun onPasswordChanged(model: Model, event: PasswordInputChanged) : Next<Model, Effect> =
        Next.next(
            model.copy(
                password = event.password,
                canLogin = verifyCreds(event.password, model.email)
            )
        )

    private fun onLoginRequested(model: Model) : Next<Model, Effect> =
        if(!model.loggingIn && model.canLogin)
            Next.next(
                model.copy(loggingIn = true),
                Effects.effects(AttemptLogin(model.email, model.password))
            )
        else
            Next.dispatch(Effects.effects(ShowErrorMessage("Can't Login")))

    private fun onLoginSuccess(model: Model) : Next<Model, Effect> =
        Next.dispatch(Effects.effects(NavigateToProfile))

    private fun onLoginFailed(model: Model, event: LoginFailed) : Next<Model, Effect> =
        Next.next(
            model.copy(loggingIn = false),
            Effects.effects(ShowErrorMessage(event.errorMsg))
        )
}