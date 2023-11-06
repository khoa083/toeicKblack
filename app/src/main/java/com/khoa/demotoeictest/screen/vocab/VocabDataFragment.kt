package com.khoa.demotoeictest.screen.vocab

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.khoa.demotoeictest.MainActivity
import com.khoa.demotoeictest.R
import com.khoa.demotoeictest.databinding.FragmentVocabDataBinding
import com.khoa.demotoeictest.model.ListVocab
import com.khoa.demotoeictest.model.ListVocabData
import com.khoa.demotoeictest.model.ListVocabDataResponse
import com.khoa.demotoeictest.model.ListVocabResponse
import com.khoa.demotoeictest.utils.DataResult
import dagger.hilt.android.AndroidEntryPoint

private const val KEY_TITLE = "title"
private const val KEY_DES = "des"
private const val KEY_TYPE = "type"
@AndroidEntryPoint
class VocabDataFragment : Fragment() {

    private var getTitle: String? = null
    private var getDes: String? = null
    private var getType: String? = null

    private lateinit var binding: FragmentVocabDataBinding
    private val viewModel: VocabViewModel by viewModels()
    private lateinit var vocabDataAdapter: VocabDataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            getTitle = it.getString(KEY_TITLE)
            getDes = it.getString(KEY_DES)
            getType = it.getString(KEY_TYPE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_vocab_data,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.getListVocabData()
        setUpRecyclerview()
        Handler(Looper.getMainLooper()).postDelayed({
            setUpObserver()
        },325)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tvTitleVocabs.text = arguments?.getString("title")
        binding.tvDesVocabs.text = arguments?.getString("des")
        binding.ivBackToHome.setOnClickListener {
            findNavController().navigateUp()
        }
        initView()
        super.onViewCreated(view, savedInstanceState)
    }
    private fun initView(){
        (activity as MainActivity).handleShowBottomNav(false)
    }

    private fun setUpRecyclerview() {
        vocabDataAdapter = VocabDataAdapter()
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        binding.rvListVocabData.apply {
            adapter = vocabDataAdapter
            setLayoutManager(layoutManager)
        }
    }

    private fun setUpObserver(){
        viewModel.getListVocabData().observe(viewLifecycleOwner) {data ->
            when(data.status) {
                DataResult.Status.SUCCESS -> {
                    showShimmer(false)
                    val listVocabData: ArrayList<ListVocabData> = ArrayList()
                    val value = data.data?.body() as ListVocabDataResponse
                    value.listVocabData?.forEach {
                        listVocabData.add(it)
                        Log.d("setUpObserver",listVocabData.toString())
                    }
                    vocabDataAdapter.submitList(listVocabData)
                    vocabDataAdapter.onClickItem = {
//                        val args = bundleOf("type" to it.type)
//                        Snackbar.make(binding.root, "ets:${it.type}",1500).show()
//                        Snackbar.make(binding.root, getType?:"",5500).show()
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


    companion object {
        @JvmStatic fun newInstance(title: String,des: String,type: String) =
                VocabDataFragment().apply {
                    arguments = Bundle().apply {
                        putString(KEY_TITLE, title)
                        putString(KEY_DES, des)
                        putString(KEY_TYPE, type)
//                        putString("key", "giá trị dữ liệu")
                    }
                }
    }

    private fun showShimmer(isShow : Boolean){
        if(isShow){
            binding.shimmer.visibility = View.VISIBLE
            binding.rvListVocabData.visibility = View.GONE
            binding.shimmer.startShimmer()
        } else {
            binding.shimmer.visibility = View.GONE
            binding.rvListVocabData.visibility = View.VISIBLE
            binding.shimmer.stopShimmer()
        }
    }

}