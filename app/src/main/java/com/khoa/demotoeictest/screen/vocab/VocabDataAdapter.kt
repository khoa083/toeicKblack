package com.khoa.demotoeictest.screen.vocab

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.khoa.demotoeictest.databinding.ItemVocabDataBinding
import com.khoa.demotoeictest.model.ListVocabData

class VocabDataAdapter : ListAdapter<ListVocabData,VocabDataAdapter.VocabDataViewHolder>(DiffCallback()){

    var onClickItem: ((ListVocabData) -> Unit)? = null

    inner class VocabDataViewHolder(private val binding: ItemVocabDataBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListVocabData){
            binding.tvTitle.text = data.vocabulary
            binding.tvTrans.text = data.translation
            binding.root.setOnClickListener {
                onClickItem?.invoke(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VocabDataViewHolder {
        return VocabDataViewHolder(ItemVocabDataBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: VocabDataViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<ListVocabData>(){
        override fun areItemsTheSame(oldItem: ListVocabData, newItem: ListVocabData): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: ListVocabData, newItem: ListVocabData): Boolean {
            return oldItem == newItem
        }

    }

}