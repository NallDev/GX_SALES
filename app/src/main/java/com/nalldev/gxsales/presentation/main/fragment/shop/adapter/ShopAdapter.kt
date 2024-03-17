package com.nalldev.gxsales.presentation.main.fragment.shop.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nalldev.gxsales.databinding.ItemShopBinding
import com.nalldev.gxsales.presentation.main.domain.model.ShopModel

class ShopAdapter : ListAdapter<ShopModel, ShopAdapter.ViewHolder>(ShopItemCallback) {
    inner class ViewHolder(private val binding : ItemShopBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data : ShopModel) = with(binding) {
            tvTitle.text = data.title
            tvPrice.text = data.price
            tvStock.text = data.stock
            tvType.text = data.type
            tvTax.text = data.tax
            ivImage.setImageResource(data.imageId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemShopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    object ShopItemCallback : DiffUtil.ItemCallback<ShopModel>() {
        override fun areItemsTheSame(oldItem: ShopModel, newItem: ShopModel) = oldItem.imageId == newItem.imageId

        override fun areContentsTheSame(oldItem: ShopModel, newItem: ShopModel) = oldItem == newItem
    }
}