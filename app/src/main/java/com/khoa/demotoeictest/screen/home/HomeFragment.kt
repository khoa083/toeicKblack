package com.khoa.demotoeictest.screen.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialSharedAxis
import com.khoa.demotoeictest.MainActivity
import com.khoa.demotoeictest.R
import com.khoa.demotoeictest.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
            duration = 300.toLong()
        }
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false).apply {
            duration = 300.toLong()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setUpListener()
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
    }

    private fun initView(){
        (activity as MainActivity).handleShowBottomNav(true)
    }

//    private fun setUpListener() {
//        val onClick = View.OnClickListener { view ->
//            setScreenParts(view)
//        }
//        binding.apply {
//            constraintListPart1.setOnClickListener(onClick)
//            constraintListPart2.setOnClickListener(onClick)
//            constraintListPart3.setOnClickListener(onClick)
//            constraintListPart4.setOnClickListener(onClick)
//            constraintListPart5.setOnClickListener(onClick)
//            constraintListPart6.setOnClickListener(onClick)
//            constraintListPart7.setOnClickListener(onClick)
//            constraintListListening.setOnClickListener(onClick)
//            constraintListReading.setOnClickListener(onClick)
//        }
//    }
//
//    private fun setScreenParts(view: View) {
//        val test = getString(R.string.test)
//        val extras = FragmentNavigatorExtras(view to test)
//        val directions = R.id.action_homeFragment_to_partsFragment
//        findNavController().navigate(directions,null,null,extras)
//    }
private fun setUpListener() {
    val buttons = arrayOf(
        binding.constraintListPart1,
        binding.constraintListPart2,
        binding.constraintListPart3,
        binding.constraintListPart4,
        binding.constraintListPart5,
        binding.constraintListPart6,
        binding.constraintListPart7,
        binding.constraintListListening,
        binding.constraintListReading
    )

    val partData = arrayOf(
        Pair("Part 1", "Mô tả tranh"),
        Pair("Part 2", "Hỏi đáp"),
        Pair("Part 3", "Đoạn hội thoại"),
        Pair("Part 4", "Bài nói chuyện ngắn"),
        Pair("Part 5", "Điền vào chỗ trống"),
        Pair("Part 6", "Hoàn thành văn bản"),
        Pair("Part 7", "Đọc hiểu văn bản"),
        Pair("Listening", "100 câu hỏi"),
        Pair("Reading", "100 câu hỏi")
    )

    buttons.forEachIndexed { index, button ->
        button.setOnClickListener {
            val (partName, partDescription) = partData[index]
            setScreenParts(button, partName, partDescription)
        }
    }
}

    private fun setScreenParts(view: View, partName: String, partDescription: String) {
        val test = getString(R.string.test)
        val extras = FragmentNavigatorExtras(view to test)
        
        val directions = R.id.action_homeFragment_to_partsFragment
        val args = bundleOf("partName" to partName, "partDescription" to partDescription)
        findNavController().navigate(directions, args, null, extras)

        exitTransition = MaterialElevationScale(false).apply {
            duration = 300.toLong()
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = 300.toLong()
        }
    }


}