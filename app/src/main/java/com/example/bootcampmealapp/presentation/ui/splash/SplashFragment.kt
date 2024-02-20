package com.example.bootcampmealapp.presentation.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bootcampmealapp.R
import com.example.bootcampmealapp.base.BaseFragment
import com.example.bootcampmealapp.databinding.FragmentSplashBinding
import com.google.firebase.auth.FirebaseAuth

class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()


    }

    private fun observe() {
        splashViewModel.navigateToSignIn.observe(viewLifecycleOwner) {
            val currentUser = FirebaseAuth.getInstance().currentUser
            if(currentUser != null) {
                val action = SplashFragmentDirections.actionSplashFragmentToHomeFragment2()
                findNavController().navigate(action)
            }
            else {
                val action = SplashFragmentDirections.actionSplashFragmentToSignInFragment()
                findNavController().navigate(action)
            }

        }
    }

}