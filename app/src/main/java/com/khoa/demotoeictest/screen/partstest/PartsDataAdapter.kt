package com.khoa.demotoeictest.screen.partstest

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.khoa.demotoeictest.databinding.ItemPartsDataBinding
import com.khoa.demotoeictest.model.PartsData
import com.khoa.demotoeictest.utils.HandleQuestions

class PartsDataAdapter : ListAdapter<PartsData, PartsDataAdapter.PartsDataViewHolder>(DiffCallback()) {

    private val arrResult = Array(103) { Array(5) { 0 } }
    private var arrRe: ArrayList<Int> = ArrayList()

    inner class PartsDataViewHolder(private val binding: ItemPartsDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: PartsData) {
            binData(data,binding)
            handleVisibleViews(data, binding)
            binding.apply {
                radioGroup1.clearCheck()
                radioGroup2.clearCheck()
                radioGroup3.clearCheck()
                radioGroup4.clearCheck()
                radioGroup5.clearCheck()
            }
            handleAnswers(data,binding)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartsDataViewHolder {
        return PartsDataViewHolder(ItemPartsDataBinding.inflate(LayoutInflater.from(parent.context),parent,false))
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

    private fun binData(data: PartsData, binding: ViewDataBinding) {
        if(binding is ItemPartsDataBinding) {
            binding.apply {
                Glide.with(ivParts.context).load(data.img).into(ivParts)
                tvSmallQues1.text = data.smallQues1
                tvSmallQues2.text = data.smallQues2
                tvSmallQues3.text = data.smallQues3
                tvSmallQues4.text = data.smallQues4
                tvSmallQues5.text = data.smallQues5
                if(data.part == "1" || data.part == "2") {
                    rdA1.text = null
                    rdB1.text = null
                    rdC1.text = null
                    rdD1.text = null
                } else {
                    rdA1.text = data.a1
                    rdB1.text = data.b1
                    rdC1.text = data.c1
                    rdD1.text = data.d1
                }
                rdA2.text = data.a2
                rdB2.text = data.b2
                rdC2.text = data.c2
                rdD2.text = data.d2
                rdA3.text = data.a3
                rdB3.text = data.b3
                rdC3.text = data.c3
                rdD3.text = data.d3
                rdA4.text = data.a4
                rdB4.text = data.b4
                rdC4.text = data.c4
                rdD4.text = data.d4
                rdA5.text = data.a5
                rdB5.text = data.b5
                rdC5.text = data.c5
                rdD5.text = data.d5
                tvQuesPart7.loadDataWithBaseURL(null,data.question ?: "","text/html","utf-8",null)
            }
        }
    }
    private fun handleVisibleViews(data: PartsData, binding: ViewDataBinding) {
        if(binding is ItemPartsDataBinding) {
            binding.apply {
                tvQuesPart7.visibility = if (data.question != "") View.VISIBLE else View.GONE
                ivParts.visibility = if (data.img != "") View.VISIBLE else View.GONE
                consTrainSmallQues1.visibility = if (data.smallQues1 != "") View.VISIBLE else View.GONE
                consTrainSmallQues2.visibility = if (data.smallQues2 != "") View.VISIBLE else View.GONE
                consTrainSmallQues3.visibility = if (data.smallQues3 != "") View.VISIBLE else View.GONE
                consTrainSmallQues4.visibility = if (data.smallQues4 != "") View.VISIBLE else View.GONE
                consTrainSmallQues5.visibility = if (data.smallQues5 != "") View.VISIBLE else View.GONE
                rdD1.visibility = if (data.d1 != "") View.VISIBLE else View.GONE
                scrollViewTop.visibility = if (data.question != "" || data.img != "") View.VISIBLE else View.GONE
                tvLineSpace.visibility = if (data.question != "" || data.img != "") View.VISIBLE else View.GONE
            }
        }
    }

    private fun handleAnswers(data: PartsData, binding: ItemPartsDataBinding) {
        binding.apply {
            val arrRadioGroups = listOf(radioGroup1, radioGroup2, radioGroup3, radioGroup4, radioGroup5)
            arrRadioGroups.forEachIndexed { groupIndex, radioGroup ->
                radioGroup.setOnCheckedChangeListener { group, checkedId ->
                    val selectedRadioButton = getSelectedRadioButton(radioGroup, checkedId)
                    val selectedText = selectedRadioButton?.text.toString()
                    val correctAnswer = when (groupIndex) {
                        0 -> data.correctAnswer1
                        1 -> data.correctAnswer2
                        2 -> data.correctAnswer3
                        3 -> data.correctAnswer4
                        4 -> data.correctAnswer5
                        else -> null
                    }
                    val pageCurrent = getPositionByItem(data)
                    checkResult(selectedText,correctAnswer,groupIndex,pageCurrent)
                    Log.d("FINDIDRD", "handleAnswers: ${group.indexOfChild(selectedRadioButton)}")
                }
            }
        //TODO: USE group.indexOfChild(selectedRadioButton) ->index
        //TODO: USE group.getChildAt(index) -> view
        }
    }

    private fun getSelectedRadioButton(radioGroup: RadioGroup, checkedId: Int): RadioButton? {
        return radioGroup.findViewById(checkedId)
    }

    private fun checkResult(selectedText: String,correctAnswer: String?,groupIndex: Int,pageCurrent: Int): ArrayList<Int>  {
        arrResult[pageCurrent][groupIndex] = if (selectedText == correctAnswer) 1 else 2
        arrRe.clear()
        arrRe = HandleQuestions.flatten2DArray(arrResult) as ArrayList<Int>
//        TODO: Chuyen mang 2 chieu sang 1 chieu
//        for (row in arrResult) {
//            for (element in row) {
//                arrRe.add(element)
//            }
//        }
        Log.d("arrQues", "PartsFragment: ${arrRe}")
        return arrRe
    }

    fun originRes():ArrayList<Int> = arrRe
    private fun getPositionByItem(data: PartsData) = currentList.indexOf(data)
//    TODO: getPositionByItem lấy vị trí trang hiện tại.
//    TODO: currentList đại diện cho danh sách dữ liệu hiện tại được hiển thị.
}