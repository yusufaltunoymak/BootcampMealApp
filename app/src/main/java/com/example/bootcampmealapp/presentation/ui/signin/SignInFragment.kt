package com.example.bootcampmealapp.presentation.ui.signin

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bootcampmealapp.R
import com.example.bootcampmealapp.base.BaseFragment
import com.example.bootcampmealapp.databinding.FragmentSignInBinding
import com.example.bootcampmealapp.util.components.CustomAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>(FragmentSignInBinding::inflate) {
    private var email: String? = null
    private var password: String? = null
    private val signInViewModel : SignInViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeState()

        binding.apply {
            loginButton.isEnabled = false
            signUpHereText.setOnClickListener {
                findNavController().navigate(R.id.signUpFragment)
            }
            passwordText.addTextChangedListener {
                password = it.toString()
                setSignUpButtonEnabled()
            }
            emailText.addTextChangedListener {
                email = it.toString()
                setSignUpButtonEnabled()
            }
            loginButton.setOnClickListener {
                signInViewModel.signInWithEmailAndPassword(email.toString(),password.toString())
            }

        }

        super.onViewCreated(view, savedInstanceState)
    }


    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            signInViewModel.signInViewState.collect {viewState ->
                viewState.currentUser?.let {
                    Log.d("userasdasd",it.email.toString())
                    val action = SignInFragmentDirections.actionSignInFragmentToHomeFragment2()
                    findNavController().navigate(action)
                }
                viewState.errorMessage?.let{
                    CustomAlertDialogBuilder.createErrorDialog(
                        errorMessage = it,
                        context = requireContext()
                    )
                }
            }
        }
    }
    private fun checkEditTextIsEmpty(): Boolean {
        val isNotEmpty =
            !(email.isNullOrEmpty() || password.isNullOrEmpty())
        return isNotEmpty
    }
    private fun setSignUpButtonEnabled() {
        val isNotEmpty = checkEditTextIsEmpty()
        binding.loginButton.isEnabled = isNotEmpty
    }
}