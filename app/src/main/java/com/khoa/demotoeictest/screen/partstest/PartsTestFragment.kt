package com.khoa.demotoeictest.screen.partstest

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.khoa.demotoeictest.MainActivity
import com.khoa.demotoeictest.R
import com.khoa.demotoeictest.databinding.FragmentPartsTestBinding
import com.khoa.demotoeictest.model.PartsData
import com.khoa.demotoeictest.model.PartsDataResponse
import com.khoa.demotoeictest.screen.intro.TabViewPagerAdapter
import com.khoa.demotoeictest.utils.DataResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PartsTestFragment : Fragment() {

    private lateinit var binding: FragmentPartsTestBinding
    private val viewModel: PartsTestViewModel by viewModels()
    private lateinit var partsDataAdapter: PartsDataAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_parts_test, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.getListDataParts()
        setUpObserver()
        return binding.root
    }

    private fun setUpObserver() {
        viewModel.getListDataParts().observe(viewLifecycleOwner) {data ->
            when(data.status) {
                DataResult.Status.SUCCESS -> {
                    val listDataParts: ArrayList<PartsData> = ArrayList()
                    val value = data.data?.body() as PartsDataResponse
                    value.listPartsData?.forEach {
                        listDataParts.add(it)
                        Log.d("setUpObserver: ", listDataParts.toString())
                    }
                    partsDataAdapter = PartsDataAdapter()
                    binding.viewPagerDataParts.adapter = partsDataAdapter
                    partsDataAdapter.submitList(listDataParts)


                }
                DataResult.Status.LOADING -> {}
                DataResult.Status.ERROR -> {}
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        (activity as MainActivity).handleShowBottomNav(false)
    }

}