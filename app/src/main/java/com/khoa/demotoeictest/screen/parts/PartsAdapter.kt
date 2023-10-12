package com.khoa.demotoeictest.screen.parts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.khoa.demotoeictest.databinding.ItemPartsBinding
import com.khoa.demotoeictest.model.Part1

class PartsAdapter : ListAdapter<Part1,PartsAdapter.PartsViewHolder>(DiffCallback()) {
    //: RecyclerView.Adapter<PartsAdapter.PartsViewHolder>()
    //: ListAdapter<Parts,PartsAdapter.PartsViewHolder>(DiffCallback())
    inner class PartsViewHolder(private val binding: ItemPartsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Part1){
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

    class DiffCallback : DiffUtil.ItemCallback<Part1>(){
        override fun areItemsTheSame(oldItem: Part1, newItem: Part1): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Part1, newItem: Part1): Boolean {
            return oldItem == newItem
        }

    }

}