package com.example.bootcampmealapp.presentation.ui.details

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.bootcampmealapp.R
import com.example.bootcampmealapp.base.BaseFragment
import com.example.bootcampmealapp.databinding.FragmentDetailsFoodBinding
import com.example.bootcampmealapp.util.Constants
import com.example.bootcampmealapp.util.components.CustomAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFoodFragment : BaseFragment<FragmentDetailsFoodBinding>(FragmentDetailsFoodBinding::inflate) {
    private val args : DetailsFoodFragmentArgs by navArgs()
    private val foodDetailsViewModel : FoodDetailsViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val food = args.food
        foodDetailsViewModel.initMeal(food)
        observeData()
        binding.apply {
            detailToolbarHeader.text = args.food.foodName
            Glide.with(requireContext())
                .load(Constants.IMAGE_BASE_URL+food.foodImageUrl)
                .into(foodDetailImage)
            foodDetailName.text = food.foodName
            foodDetailPrice.text = getString(R.string.price_format,food.foodPrice.toInt())
            totalPriceText.text = getString(R.string.price_format,food.foodPrice.toInt())

            backIcon.setOnClickListener {
                findNavController().navigate(R.id.homeFragment2)
            }

            incrementButton.setOnClickListener {
              foodDetailsViewModel.incrementQuantity()
            }
            decreaseButton.setOnClickListener {
              foodDetailsViewModel.decreaseQuantity()
            }
            addToBasketButton.setOnClickListener {
                foodDetailsViewModel.addToCart()
            }
            favoriteIcon.setOnClickListener {
                foodDetailsViewModel.addToFavorite(food)
            }

        }
        foodDetailsViewModel.favState.observe(viewLifecycleOwner){
            binding.favoriteIcon.isSelected = it.first
            if(it.second) {
                    if(it.first) {
                        Snackbar.make(view,getString(R.string.add_to_favorite_text),Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Snackbar.make(view,getString(R.string.delete_from_favorite_text),Toast.LENGTH_SHORT).show()
                    }
            }
        }

    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            foodDetailsViewModel.viewState.collect { viewState ->
                viewState.apply {
                    binding.foodCountText.text = piece.toString()
                    binding.totalPriceText.text = getString(R.string.price_format, price)
                    isAddedBasket?.let {
                        if(it == 1) {
                            findNavController().navigate(R.id.homeFragment2)
                        }
                    }
                    binding.favoriteIcon.isSelected = viewState.isFavorited

                    isCompleted.let {
                        if(it) {
                            CustomAlertDialogBuilder.createDialog(
                                requireContext(),
                                "deneme",
                                "ok"
                            )
                        }
                    }
                }
            }
        }
    }

}