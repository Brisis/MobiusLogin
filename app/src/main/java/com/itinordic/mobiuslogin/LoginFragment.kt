package com.itinordic.mobiuslogin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.itinordic.mobiuslogin.data.LoginRepository
import com.itinordic.mobiuslogin.mobius.LoginEffectHandler
import com.itinordic.mobiuslogin.mobius.LoginLogic
import com.itinordic.mobiuslogin.mobius.Model
import com.itinordic.mobiuslogin.mobius.effects.*
import com.itinordic.mobiuslogin.mobius.events.*
import com.itinordic.mobiuslogin.retrofit.LoginService
import com.spotify.mobius.*
import com.spotify.mobius.rx2.RxMobius
import kotlinx.android.synthetic.main.fragment_login.*

import io.reactivex.Observable
import io.reactivex.ObservableSource


class LoginFragment : Fragment(), View.OnClickListener {

    var navController : NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loop.dispatchEvent(EmailInputChanged(model.email))
        loop.dispatchEvent(PasswordInputChanged(model.password))
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

    private val model = Model(
        email = "",
        password = "",
        canLogin = false,
        loggingIn = false
    )

    private val loop: MobiusLoop<Model, Event, Effect> =
        Mobius.loop(LoginLogic(), LoginEffectHandler())
            .startFrom(model)

    override fun onClick(v: View?) {
        val username = username_input.text.toString()
        val password = password_input.text.toString()

        when(v!!.id){
            R.id.sign_in_button -> {
                if(username.isNotEmpty() && password.isNotEmpty()){

                    loop.dispatchEvent(EmailInputChanged(username))
                    loop.dispatchEvent(PasswordInputChanged(password))

                    loop.dispatchEvent(
                        LoginRequested
                    )
                }
                else{
                    Toast.makeText(activity, "Enter details to login", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }




}