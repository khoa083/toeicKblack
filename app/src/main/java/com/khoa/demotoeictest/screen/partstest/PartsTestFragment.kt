package com.khoa.demotoeictest.screen.partstest

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
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.khoa.demotoeictest.MainActivity
import com.khoa.demotoeictest.R
import com.khoa.demotoeictest.databinding.FragmentPartsTestBinding
import com.khoa.demotoeictest.model.PartsData
import com.khoa.demotoeictest.model.PartsDataResponse
import com.khoa.demotoeictest.utils.Constant
import com.khoa.demotoeictest.utils.DataResult
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException

@AndroidEntryPoint
class PartsTestFragment : Fragment() {

    private lateinit var binding: FragmentPartsTestBinding
    private val viewModel: PartsTestViewModel by viewModels()
    private lateinit var partsDataAdapter: PartsDataAdapter
    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false
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
        viewModel.getListDataParts().observe(viewLifecycleOwner) { data ->
            when (data.status) {
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
        handleMedia(0)
        setUpListener()
    }

    private fun initView() {
        (activity as MainActivity).handleShowBottomNav(false)
    }

    //    TODO: setUpListener(),setUpViewPager(),changePage(),handleMedia() handle button next and back page in viewPager2
    private fun setUpListener() {

        binding.ivPlay.setOnClickListener {
            playMusic()
        }

        val onClick = View.OnClickListener { view ->
            setUpViewPager(view)
        }
        binding.apply {
            ivNextParts.setOnClickListener(onClick)
            ivBackParts.setOnClickListener(onClick)
        }

        val part = arguments?.getString("part")
        binding.tvTitlePartsNumber.text = part
        binding.llFooterAudio.visibility = if (part == "listen" || part == "1" || part == "2" || part == "3" || part == "4" ) View.VISIBLE else View.GONE

        binding.seekBarLuminosite.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                mHandle.removeCallbacks(mUpdateTime)
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mHandle.removeCallbacks(mUpdateTime)
                val totalDuration = mediaPlayer?.duration
                val currentPosition: Int = Constant.progressToTimer(seekBar?.progress ?: 0, totalDuration ?: 0)
                mediaPlayer?.seekTo(currentPosition)
                updateSeekBar()
            }
        })

//        TODO: hiển thị tiến trình phụ
//        mediaPlayer?.setOnBufferingUpdateListener { mp, percent ->
//            val ratio = percent / 100.0
//            val bufferingLevel = mp.duration*ratio
//            binding.seekBarLuminosite.secondaryProgress = bufferingLevel.toInt()
//            val colorList = ColorStateList.valueOf(Color.RED)
//            binding.seekBarLuminosite.secondaryProgressTintList = colorList
//        }

    }

    private fun playMusic() {
        if (!mediaPlayer!!.isPlaying) {
            binding.ivPlay.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.button_pause))
            isPlaying = false
            mediaPlayer?.start()
        } else {
            binding.ivPlay.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.button_play))
            isPlaying = true
            mediaPlayer?.pause()
        }
    }

    private fun setUpViewPager(view: View?) {
        val currentItem = binding.viewPagerDataParts.currentItem
        when (view?.id) {
            R.id.ivNextParts -> changePage(currentItem + 1)
            R.id.ivBackParts -> changePage(currentItem - 1)
        }
    }

    private fun changePage(newPage: Int) {
        mediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
        if (newPage >= 0 && newPage < partsDataAdapter.itemCount) {
//            mediaPlayer?.stop()
            binding.viewPagerDataParts.currentItem = newPage
            handleMedia(newPage)
        }
    }

    private fun handleMedia(newPage: Int) {
        Handler(Looper.getMainLooper()).postDelayed({
            try {
                kotlin.runCatching {
                    mediaPlayer!!.reset()
                    mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
                    mediaPlayer!!.setDataSource(
                        requireContext(), Uri.parse(test[newPage])
                    )
                    mediaPlayer!!.prepare()
                    mediaPlayer!!.start()

                    binding.seekBarLuminosite.progress = 0
                    binding.seekBarLuminosite.max = 100
                    updateSeekBar()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }, 325)
    }


    //TODO: updateSeekBar() and updateTime() handle seekbar
    fun updateSeekBar() {
        mHandle.postDelayed(mUpdateTime, 100)
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
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.stop()
        }
    }

}