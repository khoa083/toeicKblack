package com.khoa.demotoeictest.screen.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.khoa.demotoeictest.databinding.ItemResultBinding

class ResultAdapter : ListAdapter<Result,ResultAdapter.ResultViewHolder>(DiffCallback()) {

    inner class ResultViewHolder(private val binding: ItemResultBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Result){
            binding.tvNumber.text = data.number.toString()
//            binding.llCheck.background = (ContextCompat.getDrawable(binding.llCheck.context,data.background))
            binding.llCheck.setBackgroundResource(data.background)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        return ResultViewHolder(ItemResultBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.number == newItem.number
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

    }

}