package com.khoa.demotoeictest.screen.result

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.khoa.demotoeictest.R
import com.khoa.demotoeictest.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
    //        val originalArray = [...] // Mảng ban đầu của bạn
//        val newArray = originalArray.take(30).toTypedArray()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_result,container,false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val originResult = arguments?.getIntegerArrayList("originResult")
        Log.d("khoa2", "ResultFragment: ${originResult.toString()}")
    }

}