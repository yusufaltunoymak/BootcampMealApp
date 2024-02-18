package com.example.bootcampmealapp.presentation.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bootcampmealapp.base.BaseFragment
import com.example.bootcampmealapp.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate) {
    private val favoriteViewModel : FavoriteViewModel by viewModels()
    private val favoriteAdapter by lazy {
        FavoriteAdapter {
            favoriteViewModel.deleteFromFavorite(it.id)
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            favoriteRv.apply {
                layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
                adapter = favoriteAdapter
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            favoriteViewModel.favoriteFoods.collect {
                if(it.isNotEmpty()){
                    favoriteAdapter.submitList(it)
                }
            }
        }
    }
}