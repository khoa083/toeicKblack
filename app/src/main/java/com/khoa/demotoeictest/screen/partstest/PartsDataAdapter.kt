package com.khoa.demotoeictest.screen.partstest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.khoa.demotoeictest.databinding.ItemPartsDataBinding
import com.khoa.demotoeictest.model.PartsData
import com.khoa.demotoeictest.screen.parts.PartsAdapter

class PartsDataAdapter: ListAdapter<PartsData,PartsDataAdapter.PartsDataViewHolder>(DiffCallback()) {
    inner class PartsDataViewHolder(private val binding: ItemPartsDataBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: PartsData) {
//            binding.apply {
//                Glide.with(binding.ivParts.context).load(data.img).into(binding.ivParts)
//                tvSmallQues1.text = data.smallQues1
//                rdA1.text  = data.a1
//                rdB1.text  = data.b1
//                rdC1.text  = data.c1
//                rdD1.text  = data.d1
//            }
            binding.binData(data)
//            binding.tvQuesPart7.loadDataWithBaseURL(null,htmlString,"text/html","utf-8",null)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartsDataViewHolder {
        return PartsDataViewHolder(ItemPartsDataBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PartsDataViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<PartsData>() {
        override fun areItemsTheSame(oldItem: PartsData, newItem: PartsData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PartsData, newItem: PartsData): Boolean {
            return oldItem == newItem
        }

    }

    fun ItemPartsDataBinding.binData(data: PartsData) {
        if(data.part == "1"){
            consTrainSmallQues2.visibility = View.GONE
            consTrainSmallQues3.visibility = View.GONE
            consTrainSmallQues4.visibility = View.GONE
            consTrainSmallQues5.visibility = View.GONE
        } else if (data.part == "2") {
            rdD1.visibility = View.GONE
            consTrainSmallQues2.visibility = View.GONE
            consTrainSmallQues3.visibility = View.GONE
            consTrainSmallQues4.visibility = View.GONE
            consTrainSmallQues5.visibility = View.GONE
        } else {
            rdD1.visibility = View.VISIBLE
            consTrainSmallQues2.visibility = View.VISIBLE
            consTrainSmallQues3.visibility = View.VISIBLE
            consTrainSmallQues4.visibility = View.VISIBLE
            consTrainSmallQues5.visibility = View.VISIBLE
        }
        Glide.with(root.context).load(data.img).into(ivParts)
        tvSmallQues1.text = data.smallQues1
        rdA1.text  = data.a1
        rdB1.text  = data.b1
        rdC1.text  = data.c1
        rdD1.text  = data.d1
    }

}