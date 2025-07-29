package com.khoa.demotoeictest.screen.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialSharedAxis
import com.kblack.base.BaseFragment
import com.khoa.demotoeictest.MainActivity
import com.khoa.demotoeictest.R
import com.khoa.demotoeictest.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override val viewModel: HomeViewModel by viewModels()
    override val layoutId = R.layout.fragment_home

    private data class PartInfo(val partName: String, val partDescription: String, val part: String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
            duration = 300.toLong()
        }
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false).apply {
            duration = 300.toLong()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.doOnPreDraw { startPostponedEnterTransition() }
    }

    override fun setupView() {
        initView()
        setUpListener()
        postponeEnterTransition()
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
        PartInfo("Part 1", "Mô tả tranh", "part_1"),
        PartInfo("Part 2", "Hỏi đáp", "part_2"),
        PartInfo("Part 3", "Đoạn hội thoại", "part_3"),
        PartInfo("Part 4", "Bài nói chuyện ngắn", "part_4"),
        PartInfo("Part 5", "Điền vào chỗ trống", "part_5"),
        PartInfo("Part 6", "Hoàn thành văn bản", "part_6"),
        PartInfo("Part 7", "Đọc hiểu văn bản", "part_7"),
        PartInfo("Listening", "100 câu hỏi", "listening"),
        PartInfo("Reading", "100 câu hỏi", "reading")
    )

    buttons.forEachIndexed { index, button ->
        button.setOnClickListener {
            val partInfo = partData[index]
            setScreenParts(button, partInfo.partName, partInfo.partDescription, partInfo.part)
        }
    }
}

    private fun setScreenParts(view: View, partName: String, partDescription: String, part: String) {
        val test = getString(R.string.test)
        val extras = FragmentNavigatorExtras(view to test)
        
        val directions = R.id.action_homeFragment_to_partsFragment
        val args = bundleOf("partName" to partName, "partDescription" to partDescription, "part" to part)
        findNavController().navigate(directions, args, null, extras)

        exitTransition = MaterialElevationScale(false).apply {
            duration = 300.toLong()
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = 300.toLong()
        }
    }


}