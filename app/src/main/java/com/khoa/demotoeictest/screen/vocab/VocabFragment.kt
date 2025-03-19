package com.khoa.demotoeictest.screen.vocab

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
import com.khoa.demotoeictest.databinding.FragmentVocabBinding
import com.khoa.demotoeictest.model.ListVocab
import com.khoa.demotoeictest.model.ListVocabResponse
import com.khoa.demotoeictest.common.utils.DataResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VocabFragment:Fragment() {

    private lateinit var binding: FragmentVocabBinding
    private val viewModel: VocabViewModel by viewModels()
    private lateinit var listVocabAdapter: ListVocabAdapter

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_vocab,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.getListVocab()
        setUpRecyclerview()
        Handler(Looper.getMainLooper()).postDelayed({
            setUpObserver()
        },325)
        return binding.root
    }

    private fun setUpRecyclerview() {
        listVocabAdapter = ListVocabAdapter()
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        binding.rvListVocab.apply {
            adapter = listVocabAdapter
            setLayoutManager(layoutManager)
        }
    }

    private fun setUpObserver() {
        viewModel.getListVocab().observe(viewLifecycleOwner) {data ->
            when(data.status) {
                DataResult.Status.SUCCESS -> {
                    showShimmer(false)
                    val listVocab: ArrayList<ListVocab> = ArrayList()
                    val value = data.data?.body() as ListVocabResponse
                    value.listVocab?.forEach {
                        listVocab.add(it)
                        Log.d("setUpObserver",listVocab.toString())
                    }
                    listVocabAdapter.submitList(listVocab)
                    listVocabAdapter.onClickItem = {
                        val args = bundleOf("title" to it.title,"des" to it.des,"type" to it.type)
//                        VocabDataFragment.newInstance(it.title?:"",it.des?:"",it.type?:"")
                        findNavController().navigate(R.id.action_vocabFragment_to_vocabDataFragment,args,null)
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
    }
    private fun initView(){
        (activity as MainActivity).handleShowBottomNav(true)
    }

    private fun showShimmer(isShow : Boolean){
        if(isShow){
            binding.shimmer.visibility = View.VISIBLE
            binding.rvListVocab.visibility = View.GONE
            binding.shimmer.startShimmer()
        } else {
            binding.shimmer.visibility = View.GONE
            binding.rvListVocab.visibility = View.VISIBLE
            binding.shimmer.stopShimmer()
        }
    }

}