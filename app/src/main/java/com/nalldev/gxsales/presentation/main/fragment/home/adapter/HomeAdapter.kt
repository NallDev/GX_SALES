package com.nalldev.gxsales.presentation.main.fragment.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nalldev.gxsales.databinding.ItemDashboardBinding
import com.nalldev.gxsales.presentation.main.domain.model.StatusesItem

class HomeAdapter : ListAdapter<StatusesItem, HomeAdapter.ViewHolder>(HomeDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDashboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    inner class ViewHolder(private val binding : ItemDashboardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data : StatusesItem) = with(binding) {
            tvTotal.text = data.total.toString()
            tvName.text = data.name
        }
    }

    object HomeDiffCallback : DiffUtil.ItemCallback<StatusesItem>() {
        override fun areItemsTheSame(oldItem: StatusesItem, newItem: StatusesItem) = oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: StatusesItem, newItem: StatusesItem) = oldItem == newItem
    }
}