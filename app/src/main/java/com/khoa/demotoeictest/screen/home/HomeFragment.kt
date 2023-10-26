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
        PartInfo("Part 1", "Mô tả tranh", "1"),
        PartInfo("Part 2", "Hỏi đáp", "2"),
        PartInfo("Part 3", "Đoạn hội thoại", "3"),
        PartInfo("Part 4", "Bài nói chuyện ngắn", "4"),
        PartInfo("Part 5", "Điền vào chỗ trống", "5"),
        PartInfo("Part 6", "Hoàn thành văn bản", "6"),
        PartInfo("Part 7", "Đọc hiểu văn bản", "7"),
        PartInfo("Listening", "100 câu hỏi", "listen"),
        PartInfo("Reading", "100 câu hỏi", "read")
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