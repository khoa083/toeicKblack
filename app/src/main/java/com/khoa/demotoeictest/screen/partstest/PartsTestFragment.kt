package com.khoa.demotoeictest.screen.partstest

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.khoa.demotoeictest.MainActivity
import com.khoa.demotoeictest.R
import com.khoa.demotoeictest.databinding.FragmentPartsTestBinding
import com.khoa.demotoeictest.model.PartsData
import com.khoa.demotoeictest.model.PartsDataResponse
import com.khoa.demotoeictest.utils.Constant
import com.khoa.demotoeictest.utils.DataResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.NonCancellable.start
import java.io.IOException

@AndroidEntryPoint
class PartsTestFragment : Fragment() {

    private lateinit var binding: FragmentPartsTestBinding
    private val viewModel: PartsTestViewModel by viewModels()
    private lateinit var partsDataAdapter: PartsDataAdapter
    private var mediaPlayer: MediaPlayer? = null
    private val test: ArrayList<String> = ArrayList()


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

                    listDataParts.forEach {
                        test.add(it.audio.toString())
                    }
                    Log.d("khoa1", test.toString())

                }
                DataResult.Status.LOADING -> {}
                DataResult.Status.ERROR -> {}
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        mediaPlayer = MediaPlayer()
        setUpViewPager()
        val part = arguments?.getString("part")
        binding.tvTitlePartsNumber.text = part
        binding.llFooterAudio.visibility = if (part=="1" || part=="2" || part=="3" || part=="4") View.VISIBLE else View.GONE

        binding.seekBarLuminosite.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) { }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) { updateSeekBar() }
        })
    }

    private fun initView() {
        (activity as MainActivity).handleShowBottomNav(false)
    }

    fun updateSeekBar() {
        mHandle.postDelayed(mUpdateTime, 100)
    }


    private fun setUpViewPager() {
        mediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
        fun changePage(newPage: Int) {
            if (newPage >= 0 && newPage < partsDataAdapter.itemCount) {
                mediaPlayer?.stop()
                binding.viewPagerDataParts.currentItem = newPage
                Handler(Looper.getMainLooper()).postDelayed({
                    try {
                        mediaPlayer?.apply {
                            reset()
                            setDataSource(test[newPage])
                            setAudioStreamType(AudioManager.STREAM_MUSIC)
                            prepare()
                            start()
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                },325)

            }
        }

        binding.ivNextParts.setOnClickListener {
            changePage(binding.viewPagerDataParts.currentItem + 1)
        }

        binding.ivBackParts.setOnClickListener {
            changePage(binding.viewPagerDataParts.currentItem - 1)
        }
    }

    private fun updateTime() {
        if (mediaPlayer == null) return
        val totalDuration = mediaPlayer?.duration
        val currentDuration = mediaPlayer?.currentPosition
        binding.tvStartTime.text =
            String.format("%s", Constant.milliSecondsToTimer(currentDuration?.toLong() ?: 0L))
        binding.tvEndTime.text =
            String.format("%s", Constant.milliSecondsToTimer(totalDuration?.toLong() ?: 0L))
        val progress = Constant.getProgressPercentage(
            currentDuration?.toLong() ?: 0L, totalDuration?.toLong() ?: 0L
        )
        binding.seekBarLuminosite.progress = progress
        mHandle.postDelayed(mUpdateTime, 100)
    }
    var mHandle = Handler(Looper.getMainLooper())
    var mUpdateTime = Runnable { updateTime() }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer = null
        mediaPlayer?.stop()
    }

    override fun onStop() {
        super.onStop()
        if(mediaPlayer?.isPlaying == true) {
            mediaPlayer?.stop()
        }
    }

}

//private fun setUpViewPager() {
//    mediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
//    binding.ivNextParts.setOnClickListener {
//        if(binding.viewPagerDataParts.currentItem + 1 < partsDataAdapter.itemCount) {
//            mediaPlayer?.stop()
//            binding.viewPagerDataParts.currentItem += 1
//            try{
//                mediaPlayer?.apply {
//                    reset();
//                    setDataSource(test[binding.viewPagerDataParts.currentItem]);
//                    setAudioStreamType(AudioManager.STREAM_MUSIC);
//                    prepare();
//                    start()
//                }
//            } catch (e: IOException){
//                e.printStackTrace()
//            }
////                Log.d("khoa1", test[binding.viewPagerDataParts.currentItem])
//        }
//    }
//
//    binding.ivBackParts.setOnClickListener {
//        if(binding.viewPagerDataParts.currentItem - 1 >= 0) {
//            mediaPlayer?.stop()
//            binding.viewPagerDataParts.currentItem -= 1
//            try{
//                mediaPlayer?.apply {
//                    reset();
//                    setDataSource(test[binding.viewPagerDataParts.currentItem]);
//                    setAudioStreamType(AudioManager.STREAM_MUSIC);
//                    prepare();
//                    start()
//                }
//            } catch (e: IOException){
//                e.printStackTrace()
//            }
////                Log.d("khoa1", test[binding.viewPagerDataParts.currentItem])
//        }
//    }
//}