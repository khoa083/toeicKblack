package com.khoa.demotoeictest.screen.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.khoa.demotoeictest.R
import com.khoa.demotoeictest.databinding.ItemVocabDataBinding
import com.khoa.demotoeictest.model.ListVocabData

class FavoriteAdapter :
        ListAdapter<ListVocabData, FavoriteAdapter.FavoriteViewHolder>(DiffCallback()) {
    var onClickItem: ((ListVocabData) -> Unit)? = null
    var onClickItemFavor: ((ListVocabData, ItemVocabDataBinding) -> Unit)? = null
    private val filteredList: MutableList<ListVocabData> = mutableListOf()
    
    
    inner class FavoriteViewHolder(private val binding: ItemVocabDataBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListVocabData) {
            binding.apply {
                tvTitle.text = data.vocabulary
                tvTrans.text = data.translation
                root.setOnClickListener {
                    onClickItem?.invoke(data)
                }
                tvFavorite.setOnClickListener {
                    onClickItemFavor?.invoke(data, this)
                }
                tvFavorite.setBackgroundResource(if (data.favorite == "0") R.drawable.ic_favorite else R.drawable.ic_favorite_fill)
            }
        }
    }
    
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            ItemVocabDataBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    
    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    class DiffCallback : DiffUtil.ItemCallback<ListVocabData>() {
        override fun areItemsTheSame(oldItem: ListVocabData, newItem: ListVocabData): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: ListVocabData, newItem: ListVocabData): Boolean {
            return oldItem == newItem
        }
        
    }
    
    fun submitFilteredList(data: List<ListVocabData>) {
        filteredList.clear()
        filteredList.addAll(data.filter { it.favorite == "1" })
        submitList(filteredList)
    }
    
}