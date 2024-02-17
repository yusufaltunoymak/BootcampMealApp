package com.example.bootcampmealapp.presentation.ui.signup

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bootcampmealapp.R
import com.example.bootcampmealapp.base.BaseFragment
import com.example.bootcampmealapp.databinding.FragmentSignUpBinding
import com.example.bootcampmealapp.util.components.CustomAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {
    private var email: String? = null
    private var password: String? = null
    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()

        binding.apply {
            signInHereText.setOnClickListener {
                findNavController().navigate(R.id.signInFragment)
            }
            signUpButton.isEnabled = false
            emailText.addTextChangedListener {
                email = it.toString()
                setSignUpButtonEnabled()
            }

            passwordText.addTextChangedListener {
                password = it.toString()
                setSignUpButtonEnabled()
            }
        }

        binding.signUpButton.setOnClickListener {
            signUpViewModel.createUserWithEmailAndPassword(
                email = email.toString(),
                password = password.toString()
            )
        }
    }

    private fun checkEditTextIsEmpty(): Boolean {
        val isNotEmpty =
            !(email.isNullOrEmpty() || password.isNullOrEmpty() || password.isNullOrEmpty())
        binding.signUpButton.isEnabled = isNotEmpty
        return isNotEmpty
    }

    private fun setSignUpButtonEnabled() {
        val isNotEmpty = checkEditTextIsEmpty()
        binding.signUpButton.isEnabled = isNotEmpty
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            signUpViewModel.signUpViewState.collect { viewState ->
                binding.apply {
                    viewState.isSignUp?.let {
                        if (it) {
                            findNavController().popBackStack()
                        }
                    }
                    viewState.errorMessage?.let {
                        CustomAlertDialogBuilder.createErrorDialog(
                            errorMessage = it,
                            context = requireContext(),
                        ) {
                            signUpViewModel.resetError()
                        }
                    }
                }
            }
        }
    }
}