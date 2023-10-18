package com.khoa.demotoeictest.screen.parts

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialContainerTransform
import com.khoa.demotoeictest.MainActivity
import com.khoa.demotoeictest.R
import com.khoa.demotoeictest.databinding.FragmentPartsBinding
import com.khoa.demotoeictest.model.Parts
import com.khoa.demotoeictest.model.PartsResponse
import com.khoa.demotoeictest.utils.DataResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PartsFragment: Fragment() {

    private lateinit var binding: FragmentPartsBinding
//    private var arrParts: ArrayList<Parts> = ArrayList()
//    private var partsAdapter: PartsAdapter? = null
    private val viewModel: PartsViewModel by viewModels()
    private lateinit var partsAdapter: PartsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_parts,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.getListParts()
        setUpRecyclerview()
        Handler(Looper.getMainLooper()).postDelayed({
            setUpObserver()
        },325)
        return binding.root
    }

    private fun setUpRecyclerview() {
        partsAdapter = PartsAdapter()
        val layoutManager =
            LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.rvListPart.apply {
            adapter = partsAdapter
            setLayoutManager(layoutManager)
        }
    }

    private fun setUpObserver() {
        viewModel.getListParts().observe(viewLifecycleOwner) {data ->
            when(data.status) {
                DataResult.Status.SUCCESS -> {
                    showShimmer(false)
                    showImageError(false)
                    binding.ivError.visibility = View.GONE
                    val listParts: ArrayList<Parts> = ArrayList()
                    val value = data.data?.body() as PartsResponse
                    value.listParts?.forEach {
                        listParts.add(it)
                        Log.d("setUpObserver",listParts.toString())
                    }
                    partsAdapter.submitList(listParts)
                    partsAdapter.onClickItem = {
                        val args = bundleOf("ets" to it.ets, "test" to it.test, "part" to it.part)
//                        Snackbar.make(binding.root, "ets:${it.ets}-test:${it.test}-part:${it.part}",1500).show()
                        findNavController().navigate(R.id.action_partsFragment_to_partsTestFragment,args,null)
                    }
                }
                DataResult.Status.LOADING -> {
                    showShimmer(true)
                }

                DataResult.Status.ERROR -> {
                    showShimmer(false)
                    showImageError(true)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
//        handleParts()
        val partName = arguments?.getString("partName")
        val partDescription = arguments?.getString("partDescription")
        binding.apply {
            tvTitleParts.text = partName
            tvDesParts.text = partDescription
            ivBackToHome.setOnClickListener{
                findNavController().navigateUp()
            }
        }
    }

    private fun initView(){
        (activity as MainActivity).handleShowBottomNav(false)
    }

//    private fun handleParts() {
//        arrParts.add(Parts("Photographs 01","6 questions","Test 1"))
//        arrParts.add(Parts("Photographs 02","6 questions","Test 2"))
//        arrParts.add(Parts("Photographs 03","6 questions","Test 3"))
//        arrParts.add(Parts("Photographs 04","6 questions","Test 4"))
//        arrParts.add(Parts("Photographs 05","6 questions","Test 5"))
//        arrParts.add(Parts("Photographs 06","6 questions","Test 6"))
//        arrParts.add(Parts("Photographs 07","6 questions","Test 7"))
//        arrParts.add(Parts("Photographs 08","6 questions","Test 8"))
//        arrParts.add(Parts("Photographs 09","6 questions","Test 9"))
//        arrParts.add(Parts("Photographs 10","6 questions","Test 10"))
//
//        binding.rvListPart.apply {
//            layoutManager = GridLayoutManager(requireContext(),1)
//            partsAdapter = PartsAdapter(arrParts)
//            adapter = partsAdapter
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.fragmentView
            duration = 300.toLong()
            scrimColor = Color.GRAY
//            setAllContainerColors(requireContext().themeColor(R.attr.colorSurface))
        }
    }

    private fun showShimmer(isShow : Boolean){
        if(isShow){
            binding.shimmer.visibility = View.VISIBLE
            binding.rvListPart.visibility = View.GONE
            binding.shimmer.startShimmer()
        } else {
            binding.shimmer.visibility = View.GONE
            binding.rvListPart.visibility = View.VISIBLE
            binding.shimmer.stopShimmer()
        }
    }

    private fun showImageError(isShow: Boolean){
        if (isShow) binding.ivError.visibility = View.VISIBLE else binding.ivError.visibility = View.GONE
    }

}