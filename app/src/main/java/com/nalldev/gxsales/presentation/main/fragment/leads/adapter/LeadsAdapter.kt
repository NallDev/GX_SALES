package com.nalldev.gxsales.presentation.main.fragment.leads.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nalldev.gxsales.databinding.ItemLeadBinding
import com.nalldev.gxsales.presentation.main.domain.model.LeadResponse

class LeadsAdapter(private val leadsAdapterEvent: LeadsAdapterEvent? = null) : ListAdapter<LeadResponse, LeadsAdapter.ViewHolder>(LeadDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeadsAdapter.ViewHolder {
        val binding = ItemLeadBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LeadsAdapter.ViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    inner class ViewHolder(private val binding : ItemLeadBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data : LeadResponse) = with(binding) {
            tvName.text = data.fullName
            tvAddress.text = data.address
            tvDate.text = data.createdAt
            tvNumber.text = String.format("#%s", data.iDNumber)
            tvBranch.text = data.branchOffice.name
            tvChannel.text = data.channel.name
            tvMedia.text = data.media.name
            tvSource.text = data.source.name
            tvStatus.text = data.status.name
            
            root.setOnClickListener {
                leadsAdapterEvent?.onItemClick(data)
            }
        }
    }

    interface LeadsAdapterEvent {
        fun onItemClick(dataLead : LeadResponse)
    }

    object LeadDiffCallback : DiffUtil.ItemCallback<LeadResponse>() {
        override fun areItemsTheSame(oldItem: LeadResponse, newItem: LeadResponse) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: LeadResponse, newItem: LeadResponse) = oldItem == newItem
    }
}