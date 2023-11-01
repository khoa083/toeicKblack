package com.khoa.demotoeictest.screen.result

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.khoa.demotoeictest.R
import com.khoa.demotoeictest.databinding.FragmentResultBinding
import kotlin.math.log

class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_result,container,false)
        binding.lifecycleOwner = this
        val originResult = arguments?.getIntegerArrayList("originResult")
        val part = arguments?.getString("part")
        handleResult(originResult,part)
        return binding.root
    }

    private fun handleResult(originResult: ArrayList<Int>?, part: String?) {
        when(part) {
            "1" -> handlePart(originResult,30)
            "2" -> handlePart(originResult,125)
//            "3" -> handlePart(originResult,195)
//            "4" -> handlePart(originResult,150)
            "5" -> handlePart(originResult,150)
            "6" -> handlePart(originResult,80)
//            "7" -> handlePart(originResult,270)
            "listen" -> {}
            "read" ->{}
            "fulltest" -> {}
        }
    }

    private fun handlePart(originResult: ArrayList<Int>?, i: Int) {
        var countTrue = 0
        var countFalse = 0
        var countNull = 0
        val arrPart = originResult?.take(i)
        val processArrPart: ArrayList<Int> = arrayListOf()

        filterArrResult(processArrPart,arrPart,i)
        Log.d("khoa2", "handlePart: $processArrPart")

        processArrPart.forEach {
            when(it){
                0 -> ++countNull
                1 -> ++countTrue
                2 -> ++countFalse
            }
        }
        setProgressBar(countTrue,processArrPart.size)
    }

    @SuppressLint("SetTextI18n")
    private fun setProgressBar(countTrue: Int, totalNumber: Int) {
        val progressPercentage = (countTrue.toFloat()/ totalNumber) * 100
        binding.ProgressBar.progress = progressPercentage.toInt()
        binding.tvResult.text = "$countTrue/$totalNumber"
    }

    private fun filterArrResult(processArrPart: ArrayList<Int>, arrPart: List<Int>?,i: Int) : ArrayList<Int>{
        if (i == 30 || i == 125 || i == 150) {
            for (index in 0 until arrPart?.size!! step 5) {
                processArrPart.add(arrPart[index])
            }
        }
//      else if (i == 195) {
//            for (index in 0 until arrPart?.size!! step 5) {
//                for (j in 0 until 3) {
//                    var count = 0
//                    processArrPart.add(arrPart[index + ++count])
//                }
//            }
//        }

        return processArrPart
    }

}