package com.itinordic.mobiuslogin.mobius

import com.itinordic.mobiuslogin.mobius.effects.AttemptLogin
import com.itinordic.mobiuslogin.mobius.effects.Effect
import com.itinordic.mobiuslogin.mobius.effects.NavigateToProfile
import com.itinordic.mobiuslogin.mobius.effects.ShowErrorMessage
import com.itinordic.mobiuslogin.mobius.events.*
import com.spotify.mobius.Effects
import com.spotify.mobius.Next

class MobiusService {
    private fun update(model: Model, event: Event): Next<Model, Effect> =
        when(event){
            is EmailInputChanged -> onEmailChanged(model, event)
            is PasswordInputChanged -> onPasswordChanged(model, event)
            is LoginRequested -> onLoginRequested(model)
            is LoginSuccessful -> onLoginSuccess(model)
            is LoginFailed -> onLoginFailed(model, event)
        }

    private fun verifyCreds(email: String, password: String) : Boolean {
        return email.isNotEmpty() && password.isNotEmpty()
    }

    private fun onEmailChanged(model: Model, event: EmailInputChanged) : Next<Model, Effect> =
        Next.next(
            model.copy(
                email = event.email,
                canLogin = verifyCreds(event.email, model.password)
            )
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
    //noChange() // tells mobius there is no state change
    //next(model)

    private fun onLoginSuccess(model: Model) : Next<Model, Effect> =
        Next.dispatch(Effects.effects(NavigateToProfile))

    private fun onLoginFailed(model: Model, event: LoginFailed) : Next<Model, Effect> =
        Next.next(
            model.copy(loggingIn = false),
            Effects.effects(ShowErrorMessage(event.errorMsg))
        )


//    fun loginHandler(attemptLogin: Observable<AttemptLogin>) =
//        attemptLogin.swithMap{
//            loginService.login(it.email, it.password)
//                .map{ result ->
//                    when(result) {
//                        is SnapshotApplyResult.Success -> LoginSuccessful
//                        is Timeout -> LoginFailed("Login Request Timed Out")
//                        is SnapshotApplyResult.Failure -> LoginFailed(result.message)
//                    }
//                }
//        }
}