package com.khoa.demotoeictest.screen.favorite

import android.annotation.SuppressLint
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialSharedAxis
import com.khoa.demotoeictest.MainActivity
import com.khoa.demotoeictest.R
import com.khoa.demotoeictest.databinding.CustomBottomsheetVocabDataBinding
import com.khoa.demotoeictest.databinding.FragmentFavoriteBinding
import com.khoa.demotoeictest.databinding.ItemVocabDataBinding
import com.khoa.demotoeictest.model.ListVocabData
import com.khoa.demotoeictest.model.ListVocabDataResponse
import com.khoa.demotoeictest.screen.vocab.VocabViewModel
import com.khoa.demotoeictest.common.utils.DataResult
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException

@AndroidEntryPoint
class FavoriteFragment: Fragment() {

    private lateinit var  binding: FragmentFavoriteBinding
    private val viewModel: VocabViewModel by viewModels()
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var favoriteAdapter: FavoriteAdapter

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite,container,false)
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
        super.onViewCreated(view, savedInstanceState)
        initView()
        mediaPlayer = MediaPlayer()
    }
    private fun initView(){
        (activity as MainActivity).handleShowBottomNav(true)
    }

    private fun setUpRecyclerview() {
        favoriteAdapter = FavoriteAdapter()
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvListFavor.apply {
            adapter = favoriteAdapter
            setLayoutManager(layoutManager)
        }
    }

    private fun setUpObserver() {
        viewModel.getListVocabData().observe(viewLifecycleOwner) { data ->
            when (data.status) {
                DataResult.Status.SUCCESS -> {
//                    showShimmer(false)
                    val listVocabData: ArrayList<ListVocabData> = ArrayList()
                    val value = data.data?.body() as ListVocabDataResponse
                    value.listVocabData?.forEach {
                        listVocabData.add(it)
                        Log.d("setUpObserver", listVocabData.toString())
                    }
                    val filteredList = listVocabData.filter { it.favorite == "1" }
                    favoriteAdapter.submitFilteredList(filteredList)
                    favoriteAdapter.onClickItem = {
                        setUpBottomSheet(it)
                    }
                    favoriteAdapter.onClickItemFavor = {dta, itemVocab ->
                        setUpFavor(dta,itemVocab)
                    }
                }

                DataResult.Status.LOADING -> {
//                    showShimmer(true)
                }

                DataResult.Status.ERROR -> {
//                    showShimmer(false)
                }
            }
        }
    }

    private fun setUpFavor(dta: ListVocabData, itemVocab: ItemVocabDataBinding) {
        when(dta.favorite) {
            "1" -> {
                viewModel.putFavoriteVocabData(dta.id?:"","0").observe(viewLifecycleOwner) { check ->
                    when(check.status) {
                        DataResult.Status.SUCCESS -> {
                            val newList = favoriteAdapter.currentList.toMutableList() // Tạo một bản sao của danh sách hiện tại
                            val updatedItem = newList.find { it.id == dta.id }
                            updatedItem?.favorite = "0" // Cập nhật yêu thích của mục đó
                            favoriteAdapter.submitList(newList) // Cập nhật danh sách
                            setUpObserver()
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

//    private fun showShimmer(isShow: Boolean) {
//        if (isShow) {
//            binding.shimmer.visibility = View.VISIBLE
//            binding.rvListFavor.visibility = View.GONE
//            binding.shimmer.startShimmer()
//        } else {
//            binding.shimmer.visibility = View.GONE
//            binding.rvListFavor.visibility = View.VISIBLE
//            binding.shimmer.stopShimmer()
//        }
//    }
}


