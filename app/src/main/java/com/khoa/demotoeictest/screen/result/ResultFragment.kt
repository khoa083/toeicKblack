package com.khoa.demotoeictest.screen.result

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.khoa.demotoeictest.R
import com.khoa.demotoeictest.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding
    private var arrayResult: ArrayList<Result> = ArrayList()
    private lateinit var resultAdapter: ResultAdapter
    private var processArrPart: ArrayList<Int> = arrayListOf()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_result,container,false)
        binding.lifecycleOwner = this
        val originResult = arguments?.getIntegerArrayList("originResult")
        val part = arguments?.getString("part")
        binding.tvTitlePart.text = "Part $part"
        handleResult(originResult,part)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView(processArrPart)
        Log.d("khoa2", "handlePart: $processArrPart")
    }

    private fun handleResult(originResult: ArrayList<Int>?, part: String?) {
        when(part) {
            "1" -> handlePart(originResult,30)
            "2" -> handlePart(originResult,125)
//            "3" -> handlePart(originResult,195)
//            "4" -> handlePart(originResult,150)
            "5" -> handlePart(originResult,150)
//            "6" -> handlePart(originResult,80)
//            "7" -> handlePart(originResult,270)
//            "listen" -> {}
//            "read" ->{}
//            "fulltest" -> {}
        }
    }

    private fun handlePart(originResult: ArrayList<Int>?, i: Int) {
        var countTrue = 0
        var countFalse = 0
        var countNull = 0
        val arrPart = originResult?.take(i)

        filterArrResult(processArrPart,arrPart,i)

        processArrPart.forEach {
            when(it){
                0 -> ++countNull
                1 -> ++countTrue
                2 -> ++countFalse
            }
        }
        binding.apply {
            tvCountNumberNull.text = countNull.toString()
            tvCountNumberTrue.text = countTrue.toString()
            tvCountNumberFalse.text = countFalse.toString()
        }
        setProgressBar(countTrue,processArrPart.size)
    }

    private fun setRecyclerView(processArrPart: ArrayList<Int>) {
        var count = 0
        processArrPart.forEach {
            when(it){
                0 -> arrayResult.add(Result(++count,R.drawable.bg_result_null))
                1 -> arrayResult.add(Result(++count,R.drawable.bg_result_true))
                2 -> arrayResult.add(Result(++count,R.drawable.bg_result_false))
            }
        }
        binding.rvListResult.apply {
            layoutManager = GridLayoutManager(requireContext(),5)
            resultAdapter = ResultAdapter()
            adapter = resultAdapter
            resultAdapter.submitList(arrayResult)
        }
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