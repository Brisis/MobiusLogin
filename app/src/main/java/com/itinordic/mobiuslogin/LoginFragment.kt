package com.itinordic.mobiuslogin

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.itinordic.mobiuslogin.data.model.LoggedInUser
import com.itinordic.mobiuslogin.mobius.LoginEffectHandler
import com.itinordic.mobiuslogin.mobius.LoginLogic
import com.itinordic.mobiuslogin.mobius.Model
import com.itinordic.mobiuslogin.mobius.effects.*
import com.itinordic.mobiuslogin.mobius.events.*
import com.spotify.mobius.*
import kotlinx.android.synthetic.main.fragment_login.*

import kotlinx.coroutines.launch

var ACCESS_TOKEN = ""

class LoginFragment : Fragment(), View.OnClickListener {

    private var accessToken: String? = null

    private var navController : NavController? = null
    lateinit var model: Model
    lateinit var loop: MobiusLoop<Model, Event, Effect>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = Model(
            email = "",
            password = "",
            canLogin = false,
            loggingIn = false
        )

        // Whenever the view is resumed, subscribe to our model's state
        lifecycleScope.launchWhenResumed {
            loop = Mobius.loop(LoginLogic(), LoginEffectHandler())
                .startFrom(model)
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        fun usernameChanged(newUsername : String) {
            val event = EmailInputChanged(newUsername)
            Log.i("Message Email", newUsername)
            loop.dispatchEvent(event)
        }

        fun passwordChanged(newPassword : String) {
             val event = PasswordInputChanged(newPassword)
            Log.i("Message Password", newPassword)
            loop.dispatchEvent(event)
        }

        username_input.doOnTextChanged { text, _, _, _ ->
            usernameChanged(text?.toString().orEmpty())
        }

        password_input.doOnTextChanged { text, _, _, _ ->
            passwordChanged(text?.toString().orEmpty())
        }

        view.findViewById<Button>(R.id.sign_in_button).setOnClickListener(this)
    }



    override fun onClick(v: View?) {
        val username = username_input.text.toString()
        val password = password_input.text.toString()

        when(v!!.id){
            R.id.sign_in_button -> {
                if(username.isNotEmpty() && password.isNotEmpty()){
                    loop.dispatchEvent(
                        LoginRequested
                    )

                    val bundle = bundleOf(
                        "username" to username,
                    )

                    navController!!.navigate(
                        R.id.action_loginFragment2_to_profileFragment2,
                        bundle
                    )
                }
                else{
                    Toast.makeText(activity, "Enter details to login", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(accessToken: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ACCESS_TOKEN, accessToken)
                }
            }
    }

//    val newModel = loop.mostRecentModel!!
//    Log.i("New Model", "${newModel.email}, ${newModel.password}, ${newModel.canLogin}, ${newModel.canLogin}")

}