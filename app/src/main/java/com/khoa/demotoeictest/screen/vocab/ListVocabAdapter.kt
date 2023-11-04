package com.khoa.demotoeictest.screen.vocab

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.khoa.demotoeictest.databinding.ItemListVocabBinding
import com.khoa.demotoeictest.model.ListVocab

class ListVocabAdapter : ListAdapter<ListVocab,ListVocabAdapter.ListVocabViewHolder>(DiffCallback()){

    var onClickItem: ((ListVocab) -> Unit)? = null

    inner class ListVocabViewHolder(private val binding: ItemListVocabBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListVocab){
            binding.apply {
                Glide.with(ivImgTitleListVocab.context).load(data.img).into(ivImgTitleListVocab)
                tvTitleListVocab.text = data.title
                tvDesListVocab.text = data.des
                root.setOnClickListener {
                    onClickItem?.invoke(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListVocabViewHolder {
        return ListVocabViewHolder(ItemListVocabBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ListVocabViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<ListVocab>(){
        override fun areItemsTheSame(oldItem: ListVocab, newItem: ListVocab): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ListVocab, newItem: ListVocab): Boolean {
            return oldItem == newItem
        }

    }

}