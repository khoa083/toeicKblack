package com.khoa.demotoeictest.screen.result

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.khoa.demotoeictest.R
import com.khoa.demotoeictest.databinding.FragmentResultBinding
import com.khoa.demotoeictest.utils.HandleQuestions.Companion.calculatorScore

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
        showScore(part, originResult)
        Log.d("khoa2gfg", "handlePart: ${originResult?.size}-${originResult}")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView(processArrPart)
        setUpListener()
        Log.d("khoa2", "handlePart: $processArrPart")
    }

    private fun setUpListener() {
        val onClick = View.OnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_homeFragment)
        }
        binding.tvReturnToHome.setOnClickListener(onClick)
    }

    private fun handleResult(originResult: ArrayList<Int>?, part: String?) {
        when(part) {
            "1" -> handlePart(originResult,30)
            "2" -> handlePart(originResult,125)
            "3" -> handlePart(originResult,65)
            "4" -> handlePart(originResult,50)
            "5" -> handlePart(originResult,150)
            "6" -> handlePart(originResult,20)
            "7" -> handlePart(originResult,75)
            "listen" -> handlePart(originResult,270)
            "read" -> handlePart(originResult,245)
            "" -> handlePart(originResult,515)
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
                1 -> ++countTrue
                2 -> ++countFalse
                3 -> ++countNull
            }
        }
        binding.apply {
            tvCountNumberTrue.text = countTrue.toString()
            tvCountNumberFalse.text = countFalse.toString()
            tvCountNumberNull.text = countNull.toString()
        }
        val totalQues = (countTrue+countFalse+countNull)
        setProgressBar(countTrue,totalQues)
    }

    private fun setRecyclerView(processArrPart: ArrayList<Int>) {
        var count = 0
        processArrPart.forEach {
            when(it){
                1 -> arrayResult.add(Result(++count,R.drawable.bg_result_true))
                2 -> arrayResult.add(Result(++count,R.drawable.bg_result_false))
                3 -> arrayResult.add(Result(++count,R.drawable.bg_result_null))
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
        for (index in 0 until arrPart?.size!!) {
            processArrPart.add(arrPart[index])
        }
//        TODO: Cách gỉai quyết issue này get ra các smallQues<1,2,3,4,5> rồi đưa vào mảng
//        TODO: VD: Part1 [3,0,0,0,0,3,0,0,0,0,...] Part6 [3,3,3,3,0,3,3,3,3,0,...] đếm số 3 la so câu hỏi

        return processArrPart
    }

    private fun showScore(part: String?, arr: ArrayList<Int>?) {
        if(part=="") {
            binding.apply {
                constrainProgress.layoutParams = (constrainProgress.layoutParams as ViewGroup.LayoutParams).apply {
                    width = ViewGroup.LayoutParams.WRAP_CONTENT
                }
                val scoreListen = calculatorScore(arr,"listen").toString()
                val scoreRead = calculatorScore(arr,"read").toString()
                val scoreTotal = (calculatorScore(arr,"listen")+calculatorScore(arr,"read")).toString()
                tvScoreListen.text = scoreListen
                tvScoreRead.text = scoreRead
                tvScoreTotal.text = scoreTotal
            }
        } else {
            binding.constrainProgress.layoutParams = (binding.constrainProgress.layoutParams as ViewGroup.LayoutParams).apply {
                width = ViewGroup.LayoutParams.MATCH_PARENT
            }
        }
    }

}