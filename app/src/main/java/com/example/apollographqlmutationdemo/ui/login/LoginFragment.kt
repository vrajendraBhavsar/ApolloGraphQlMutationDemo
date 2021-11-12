package com.example.apollographqlmutationdemo.ui.login

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.apollographql.apollo.api.Response
import com.example.apollographqldemozz.UserLoginMutation
import com.example.apollographqlmutationdemo.data.model.state.ViewState
import com.example.apollographqlmutationdemo.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding : FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)

        binding.btnIndividual.setOnClickListener {
            if (!binding.etEmail.text.isNullOrEmpty()){
                viewModel.getUserLoginResult(binding.etEmail.text.toString())
                Log.d("VRAJTEST", "onCreateView: user entered Email : ${binding.etEmail.text.toString()}")
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
            }
            is ViewState.Error -> {
                Toast.makeText(requireContext(), "graphQl ERROR", Toast.LENGTH_SHORT).show()
            }
        }
    }
}