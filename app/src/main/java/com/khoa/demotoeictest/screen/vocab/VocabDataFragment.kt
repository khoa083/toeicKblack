package com.khoa.demotoeictest.screen.vocab

import android.annotation.SuppressLint
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
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
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.khoa.demotoeictest.MainActivity
import com.khoa.demotoeictest.R
import com.khoa.demotoeictest.databinding.CustomBottomsheetVocabDataBinding
import com.khoa.demotoeictest.databinding.FragmentVocabDataBinding
import com.khoa.demotoeictest.databinding.ItemVocabDataBinding
import com.khoa.demotoeictest.model.ListVocab
import com.khoa.demotoeictest.model.ListVocabData
import com.khoa.demotoeictest.model.ListVocabDataResponse
import com.khoa.demotoeictest.model.ListVocabResponse
import com.khoa.demotoeictest.utils.DataResult
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException

//private const val KEY_TITLE = "title"
//private const val KEY_DES = "des"
//private const val KEY_TYPE = "type"

@AndroidEntryPoint
class VocabDataFragment : Fragment() {

    private var getTitle: String? = null
    private var getDes: String? = null
    private var getType: String? = null

    private lateinit var binding: FragmentVocabDataBinding
    private val viewModel: VocabViewModel by viewModels()
    private lateinit var vocabDataAdapter: VocabDataAdapter
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            getTitle = it.getString(KEY_TITLE)
//            getDes = it.getString(KEY_DES)
//            getType = it.getString(KEY_TYPE)
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_vocab_data, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.getListVocabData()
        setUpRecyclerview()
        Handler(Looper.getMainLooper()).postDelayed({
            setUpObserver()
        }, 325)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tvTitleVocabs.text = arguments?.getString("title")
        binding.tvDesVocabs.text = arguments?.getString("des")
        binding.ivBackToHome.setOnClickListener {
            findNavController().navigateUp()
        }
        initView()
        mediaPlayer = MediaPlayer()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initView() {
        (activity as MainActivity).handleShowBottomNav(false)
    }

    private fun setUpRecyclerview() {
        vocabDataAdapter = VocabDataAdapter()
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvListVocabData.apply {
            adapter = vocabDataAdapter
            setLayoutManager(layoutManager)
        }
    }

    private fun setUpObserver() {
        viewModel.getListVocabData().observe(viewLifecycleOwner) { data ->
            when (data.status) {
                DataResult.Status.SUCCESS -> {
                    showShimmer(false)
                    val listVocabData: ArrayList<ListVocabData> = ArrayList()
                    val value = data.data?.body() as ListVocabDataResponse
                    value.listVocabData?.forEach {
                        listVocabData.add(it)
                        Log.d("setUpObserver", listVocabData.toString())
                    }
                    vocabDataAdapter.submitList(listVocabData)
                    vocabDataAdapter.onClickItem = {
                        setUpBottomSheet(it)
                    }
                    vocabDataAdapter.onClickItemFavor = {dta, itemVocab ->
                        setUpFavor(dta,itemVocab)
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

    private fun setUpFavor(dta: ListVocabData, itemVocab: ItemVocabDataBinding) {
        when(dta.favorite) {
            "0" -> {
                viewModel.putFavoriteVocabData(dta.id?:"","1").observe(viewLifecycleOwner) {check ->
                    when(check.status) {
                        DataResult.Status.SUCCESS -> {
                            val newList = vocabDataAdapter.currentList.toMutableList() // Tạo một bản sao của danh sách hiện tại
                            val updatedItem = newList.find { it.id == dta.id }
                            updatedItem?.favorite = "1" // Cập nhật yêu thích của mục đó
                            vocabDataAdapter.submitList(newList) // Cập nhật danh sách
                        }
                        DataResult.Status.ERROR -> {}
                        else -> {}
                    }
                }
                itemVocab.tvFavorite.setBackgroundResource(R.drawable.ic_favorite_fill)
                Snackbar.make(binding.root,"Đã thêm \"${dta.vocabulary}\" vào yêu thích",800).show()

            }
            "1" -> {
                viewModel.putFavoriteVocabData(dta.id?:"","0").observe(viewLifecycleOwner) { check ->
                    when(check.status) {
                        DataResult.Status.SUCCESS -> {
                            val newList = vocabDataAdapter.currentList.toMutableList() // Tạo một bản sao của danh sách hiện tại
                            val updatedItem = newList.find { it.id == dta.id }
                            updatedItem?.favorite = "0" // Cập nhật yêu thích của mục đó
                            vocabDataAdapter.submitList(newList) // Cập nhật danh sách
                        }
                        DataResult.Status.ERROR -> {}
                        else -> {}
                    }
                }
                itemVocab.tvFavorite.setBackgroundResource(R.drawable.ic_favorite)
                Snackbar.make(binding.root,"Đã xoá \"${dta.vocabulary}\" khỏi yêu thích",800).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUpBottomSheet(data: ListVocabData) {
        val bottomSheet = BottomSheetDialog(requireContext(), R.style.customBottomSheetDialog)
        val binding = CustomBottomsheetVocabDataBinding.inflate(layoutInflater)
        bottomSheet.setContentView(binding.root)
        bottomSheet.setCancelable(true)
        bottomSheet.dismissWithAnimation = true
//        bottomSheet.window!!.attributes.windowAnimations = R.style.animBottomSheet

        binding.apply {
            Glide.with(ivVocab.context).load(data.img).into(ivVocab)
            tvVocabulary.text = data.vocabulary
            tvPronunciation.text = data.pronunciation
            tvMeans.text = data.translation
            tvDefine.text = data.meaning
            tvTransEng.text = "- ${data.exampleEng}"
            tvTransVn.text = "- ${data.exampleVn}"
        }
        bottomSheet.create()
        bottomSheet.show()

        binding.ivAudio.setOnClickListener {
            Handler(Looper.getMainLooper()).postDelayed({
                try {
                    kotlin.runCatching {
                        mediaPlayer!!.reset()
                        mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
                        mediaPlayer!!.setDataSource(
                            requireContext(), Uri.parse(data.audio)
                        )
                        mediaPlayer!!.prepare()
                        mediaPlayer!!.start()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }, 325)
        }
    }


//    companion object {
//        @JvmStatic
//        fun newInstance(title: String, des: String, type: String) =
//            VocabDataFragment().apply {
//                arguments = Bundle().apply {
//                    putString(KEY_TITLE, title)
//                    putString(KEY_DES, des)
//                    putString(KEY_TYPE, type)
////                        putString("key", "giá trị dữ liệu")
//                }
//            }
//    }

    private fun showShimmer(isShow: Boolean) {
        if (isShow) {
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