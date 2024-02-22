package com.example.bootcampmealapp.presentation.ui.order

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bootcampmealapp.R
import com.example.bootcampmealapp.databinding.BasketRecyclerItemBinding
import com.example.bootcampmealapp.domain.model.remote.basket.BasketFoods
import com.example.bootcampmealapp.util.Constants

class BasketAdapter(
    private val onItemClicked: (BasketFoods) -> Unit
) : ListAdapter<BasketFoods, BasketAdapter.ViewHolder>(BasketDiffCallBack()) {
    inner class ViewHolder(private val binding : BasketRecyclerItemBinding, private val context : Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : BasketFoods) {
            binding.apply {
                foodBasketName.text = item.foodName
                val priceText = "${context.getString(R.string.price_text)} ${item.foodPrice!!.toInt() * item.foodPiece!!.toInt()} ₺"
                foodBasketPrice.text = priceText
                val quantityText = "${context.getString(R.string.quantity_text)} ${item.foodPiece}"
                foodBasketQuantity.text = quantityText
                Glide.with(context).load(Constants.IMAGE_BASE_URL+"${item.foodImageUrl}")
                    .into(foodBasketImage)
                deleteIcon.setOnClickListener {
                    onItemClicked.invoke(item)
                }

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BasketRecyclerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding,parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}


class BasketDiffCallBack : DiffUtil.ItemCallback<BasketFoods>() {
    override fun areItemsTheSame(oldItem: BasketFoods, newItem: BasketFoods): Boolean {
        return oldItem.foodName == newItem.foodName

    }

    override fun areContentsTheSame(oldItem: BasketFoods, newItem: BasketFoods): Boolean {
        return oldItem == newItem
    }

}