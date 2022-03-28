package com.itinordic.mobiuslogin

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.itinordic.mobiuslogin.mobius.Model
import com.itinordic.mobiuslogin.mobius.effects.AttemptLogin
import com.itinordic.mobiuslogin.mobius.effects.Effect
import com.itinordic.mobiuslogin.mobius.effects.NavigateToProfile
import com.itinordic.mobiuslogin.mobius.effects.ShowErrorMessage
import com.itinordic.mobiuslogin.mobius.events.*
import com.spotify.mobius.Effects.effects
import com.spotify.mobius.Next
import com.spotify.mobius.Next.*
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(), View.OnClickListener {

    var navController : NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.sign_in_button).setOnClickListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.sign_in_button -> {
                if(!TextUtils.isEmpty(username_input.text.toString()) || !TextUtils.isEmpty(password_input.text.toString())){
                    update(model = Model(username_input.text.toString(), password_input.text.toString(), true, loggingIn = true), event = LoginRequested)
//                    var bundle = bundleOf(
//                        "recipient" to recipient,
//                        "amount" to amount
//                    )
//                    navController!!.navigate(
//                        R.id.action_loginFragment2_to_profileFragment2,
//
//                    )
                }
                else{
                    Toast.makeText(activity, "Enter details to login", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.sign_in_button -> navController!!.navigate(R.id.action_loginFragment_to_profileFragment)
        }
    }

    fun update(model: Model, event: Event): Next<Model, Effect> =
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
        next(model.copy(
            email = event.email,
            canLogin = verifyCreds(event.email, model.password)
        ))

    private fun onPasswordChanged(model: Model, event: PasswordInputChanged) : Next<Model, Effect> =
        next(model.copy(
            password = event.password,
            canLogin = verifyCreds(event.password, model.email)
        ))

    private fun onLoginRequested(model: Model) : Next<Model, Effect> =
        if(!model.loggingIn && model.canLogin)
            next(
                model.copy(loggingIn = true),
                effects(AttemptLogin(model.email, model.password))
            )
        else
            dispatch(effects(ShowErrorMessage("Can't Login")))
            //noChange() // tells mobius there is no state change
            //next(model)

    private fun onLoginSuccess(model: Model) : Next<Model, Effect> =
        dispatch(effects(NavigateToProfile))

    private fun onLoginFailed(model: Model, event: LoginFailed) : Next<Model, Effect> =
        next(
            model.copy(loggingIn = false),
            effects(ShowErrorMessage(event.errorMsg))
        )
}