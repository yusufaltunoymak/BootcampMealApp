package com.example.bootcampmealapp.presentation.ui.favorite

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bootcampmealapp.data.local.FoodEntity
import com.example.bootcampmealapp.databinding.BasketRecyclerItemBinding
import com.example.bootcampmealapp.util.Constants

class FavoriteAdapter(private val onDeleteItemClicked : (FoodEntity) -> Unit) : androidx.recyclerview.widget.ListAdapter<FoodEntity,FavoriteAdapter.ViewHolder>(FavoriteDiffCallBack()) {
    inner class ViewHolder(
        private val binding : BasketRecyclerItemBinding,
        private val context : Context
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(foodEntity: FoodEntity) {
            binding.apply {
                foodBasketName.text = foodEntity.foodName
                foodBasketPrice.text = "${foodEntity.foodPrice.toString()} ₺"
                Glide.with(context).load(Constants.IMAGE_BASE_URL + "${foodEntity.foodImageUrl}")
                    .into(foodBasketImage)
                deleteIcon.setOnClickListener {
                    onDeleteItemClicked.invoke(foodEntity)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            BasketRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class FavoriteDiffCallBack : DiffUtil.ItemCallback<FoodEntity>() {
    override fun areItemsTheSame(oldItem: FoodEntity, newItem: FoodEntity): Boolean {
        return oldItem == newItem

    }

    override fun areContentsTheSame(oldItem: FoodEntity, newItem: FoodEntity): Boolean {
        return oldItem == newItem
    }

}