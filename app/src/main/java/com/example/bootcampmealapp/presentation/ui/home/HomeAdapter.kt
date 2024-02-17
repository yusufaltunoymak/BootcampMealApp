package com.example.bootcampmealapp.presentation.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bootcampmealapp.databinding.HomeRecyclerItemBinding
import com.example.bootcampmealapp.domain.model.remote.FoodResponse
import com.example.bootcampmealapp.util.Constants

class HomeAdapter(private val onItemClicked : (food : FoodResponse) -> Unit) : ListAdapter<FoodResponse,HomeAdapter.ViewHolder>(HomeDiffCallBack()) {
    inner class ViewHolder(private val binding : HomeRecyclerItemBinding, private val context : Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(food: FoodResponse) {
            binding.apply {
                foodNameText.text = food.foodName
                foodPriceText.text = "${food.foodPrice.toString()} ₺"
                Glide.with(context).load(Constants.IMAGE_BASE_URL + "${food.foodImageUrl}")
                    .into(foodImage)

                root.setOnClickListener {
                    onItemClicked.invoke(food)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HomeRecyclerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding,parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class HomeDiffCallBack : DiffUtil.ItemCallback<FoodResponse>() {
    override fun areItemsTheSame(oldItem: FoodResponse, newItem: FoodResponse): Boolean {
        return oldItem == newItem

    }

    override fun areContentsTheSame(oldItem: FoodResponse, newItem: FoodResponse): Boolean {
        return oldItem == newItem
    }

}