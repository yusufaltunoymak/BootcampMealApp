package com.example.bootcampmealapp.presentation.ui.home

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bootcampmealapp.R
import com.example.bootcampmealapp.base.BaseFragment
import com.example.bootcampmealapp.databinding.FragmentHomeBinding
import com.example.bootcampmealapp.util.components.CustomAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val homeViewModel : HomeViewModel by viewModels()
    private val homeAdapter by lazy {
        HomeAdapter {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragment2ToDetailsFoodFragment(it)
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        binding.apply {
            foodsRv.apply {
                layoutManager = GridLayoutManager(requireContext(),2)
                adapter = homeAdapter
            }
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let { query ->
                        search(query)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let { newText ->
                        search(newText)
                    }
                    return true
                }
            })
            logOutIcon.setOnClickListener {
                CustomAlertDialogBuilder.createDialog(
                   context = requireContext(),
                    message = getString(R.string.are_you_sure_exit),
                   positiveButtonText =  getString(R.string.okText),
                    negativeButtonText = getString(R.string.no_text),
                    positiveButtonClickListener = {
                        homeViewModel.signOut()
                        findNavController().navigate(R.id.signInFragment)
                    }
                    ) { }

            }
        }
    }
    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.viewState.collect { viewState ->
                viewState.apply {
                    foods?.let { foodList ->
                        homeAdapter.submitList(foodList)
                    }
                }
            }
        }
    }
    fun search(searchWord: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.search(searchWord)
        }
    }

    override fun onResume() {
        super.onResume()
        observeData()
    }
}