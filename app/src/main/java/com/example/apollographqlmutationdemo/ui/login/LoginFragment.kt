package com.example.apollographqlmutationdemo.ui.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.apollographql.apollo.api.Response
import com.example.apollographqldemozz.UserLoginMutation
import com.example.apollographqlmutationdemo.R
import com.example.apollographqlmutationdemo.data.model.state.ViewState
import com.example.apollographqlmutationdemo.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment @Inject constructor(): Fragment() {
    private lateinit var binding : FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    private var PRIVATE_MODE = Context.MODE_PRIVATE
    private val PREF_NAME = "bearer-token"
    private val TOKEN_KEY: String = "bearer-token-key"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)

        binding.btnLogin.setOnClickListener {
            if (!binding.etEmail.text.isNullOrEmpty()){
                viewModel.getUserLoginResult(binding.etEmail.text.toString(), requireContext())
                Log.d("VRAJTEST", "onCreateView: user entered Email : ${binding.etEmail.text.toString()}")
                Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_tripActionFragment)
            }else{
                Toast.makeText(requireContext(), "Enter email first", Toast.LENGTH_SHORT).show()
                val emailInput: String = binding.etEmail.text.toString()
                if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches() && !binding.etEmail.text.isNullOrEmpty()) {
                    Toast.makeText(requireContext(), "Please enter correct email", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.userLoginResult.observe(viewLifecycleOwner, ::handleLoginResult)
    }

    private fun handleLoginResult(viewState: ViewState<Response<UserLoginMutation.Data>>?) {
        when (viewState) {
            is ViewState.Success -> {
                val emailResponse = viewState.value?.data?.login
                Toast.makeText(requireContext(), "Email response: $emailResponse", Toast.LENGTH_LONG).show()
                if (emailResponse != "null") {
                    //Shared Preference
                    val sharedPref: SharedPreferences = requireContext().getSharedPreferences(PREF_NAME, PRIVATE_MODE)
                    val editor = sharedPref.edit()
                    editor.putString(TOKEN_KEY, emailResponse)
                    editor.apply()

//                    val action = emailResponse?.let { LoginFragmentDirections.actionLoginFragmentToTripActionFragment(token = it) }
//                    action?.let { Navigation.findNavController(requireView()).navigate(it) }
                }
            }
            is ViewState.Error -> {
                Toast.makeText(requireContext(), "graphQl login ERROR", Toast.LENGTH_SHORT).show()
            }
            is ViewState.Loading -> TODO()
            null -> TODO()
        }
    }
}