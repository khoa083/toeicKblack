package com.khoa.demotoeictest.screen.intro

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.khoa.demotoeictest.databinding.ItemIntroBinding

class TabViewPagerAdapter(private val list: ArrayList<ItemIntro>, private val viewPager2: ViewPager2): RecyclerView.Adapter<TabViewPagerAdapter.IntroViewHolder>() {

    inner class IntroViewHolder(private val binding: ItemIntroBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(itemIntro: ItemIntro){
            binding.ivIntro.setImageDrawable(ContextCompat.getDrawable(binding.ivIntro.context,itemIntro.image))
            binding.tvTitleIntro.text = itemIntro.title
            binding.tvDesIntro.text = itemIntro.des
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroViewHolder {
        return IntroViewHolder(ItemIntroBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: IntroViewHolder, position: Int) = holder.bind(list[position])

}