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

    private val loginRepository = LoginService()

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.sign_in_button -> {
                val username = username_input.text.toString()
                val password = password_input.text.toString()

                if(username.isNotEmpty() && password.isNotEmpty()){

                    val doLogin = loginRepository.login(username,password)
                    if (doLogin){
                        val bundle = bundleOf(
                            "username" to username,
                        )
                        navController!!.navigate(
                            R.id.action_loginFragment2_to_profileFragment2,
                            bundle
                        )
                    }

                }
                else{
                    Toast.makeText(activity, "Enter details to login", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}