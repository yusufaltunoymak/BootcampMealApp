package com.example.bootcampmealapp.presentation.ui.order

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bootcampmealapp.R
import com.example.bootcampmealapp.base.BaseFragment
import com.example.bootcampmealapp.databinding.FragmentOrderBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrderFragment : BaseFragment<FragmentOrderBinding>(FragmentOrderBinding::inflate) {
    private val orderViewModel : OrderViewModel by viewModels()

    private val basketAdapter by lazy {
        BasketAdapter {
            orderViewModel.deleteFoodFromBasket(it.id!!.toInt())
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        binding.apply {
            foodBasketRv.apply {
                layoutManager =LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
                adapter = basketAdapter
            }
            orderViewModel.getBasketFoods()
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            orderViewModel.viewState.collect { viewState ->
                viewState.basketFoods?.let {
                    basketAdapter.submitList(it)
                }
                viewState.totalPrice?.let {
                    binding.totalTv.text = getString(R.string.total_price_text,it)
                }
            }
        }
    }

}