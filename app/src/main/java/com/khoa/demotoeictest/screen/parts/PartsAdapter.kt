package com.khoa.demotoeictest.screen.parts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.khoa.demotoeictest.databinding.ItemPartsBinding
import com.khoa.demotoeictest.model.Parts

class PartsAdapter : ListAdapter<Parts,PartsAdapter.PartsViewHolder>(DiffCallback()) {
    //: RecyclerView.Adapter<PartsAdapter.PartsViewHolder>()
    //: ListAdapter<Parts,PartsAdapter.PartsViewHolder>(DiffCallback())
    inner class PartsViewHolder(private val binding: ItemPartsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Parts){
            binding.tvTitle.text = data.title?:""
            binding.tvQues.text = data.numQuestions?:""
            binding.tvTestSets.text = data.des?:""
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartsViewHolder {
        return PartsViewHolder(ItemPartsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PartsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Parts>(){
        override fun areItemsTheSame(oldItem: Parts, newItem: Parts): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Parts, newItem: Parts): Boolean {
            return oldItem == newItem
        }

    }

}