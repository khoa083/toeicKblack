package com.khoa.demotoeictest.screen.fulltest

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
import com.google.android.material.transition.MaterialSharedAxis
import com.khoa.demotoeictest.MainActivity
import com.khoa.demotoeictest.R
import com.khoa.demotoeictest.databinding.FragmentTestfullBinding
import com.khoa.demotoeictest.model.Parts
import com.khoa.demotoeictest.model.PartsResponse
import com.khoa.demotoeictest.screen.parts.PartsAdapter
import com.khoa.demotoeictest.screen.parts.PartsViewModel
import com.khoa.demotoeictest.common.utils.DataResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FulltestFragment: Fragment() {

    private lateinit var binding: FragmentTestfullBinding
    private val viewModel: PartsViewModel by viewModels()
    private lateinit var partsAdapter: PartsAdapter

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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_testfull,container,false)
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
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
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
                        findNavController().navigate(R.id.action_fulltestFragment_to_partsTestFragment,args,null)
                    }
                }
                DataResult.Status.LOADING -> {
                    showShimmer(true)
                }

                DataResult.Status.ERROR -> {
                    showShimmer(false)
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
        }
    }

    private fun initView(){
        (activity as MainActivity).handleShowBottomNav(true)
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

}