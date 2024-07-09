package com.khoa.demotoeictest.screen.partstest

import android.annotation.SuppressLint
import android.app.AlertDialog
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
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.khoa.demotoeictest.MainActivity
import com.khoa.demotoeictest.R
import com.khoa.demotoeictest.databinding.CustomDialogSubmitPartsBinding
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
    private val arrAudio: ArrayList<String> = ArrayList()
    private val arrType: ArrayList<String> = ArrayList()
    private val arrParts: ArrayList<String> = ArrayList()
    private var dialog: AlertDialog? = null
    private val newArrResult: ArrayList<Int> = ArrayList()
//    private val bundle = Bundle()


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
                    showLoading(false)
                    val listDataParts: ArrayList<PartsData> = ArrayList()
                    val value = data.data?.body() as PartsDataResponse
                    value.listPartsData?.forEach {
                        listDataParts.add(it)
                        Log.d("setUpObserver: ", listDataParts.toString())
                    }
                    partsDataAdapter = PartsDataAdapter()
                    binding.viewPagerDataParts.adapter = partsDataAdapter
                    partsDataAdapter.submitList(listDataParts)
                    binding.viewPagerDataParts.offscreenPageLimit = partsDataAdapter.itemCount + 1
//                    binding.viewPagerDataParts.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT

                    listDataParts.forEach {
                        arrAudio.add(it.audio.toString())
                        arrType.add(it.type.toString())
                        arrParts.add(it.part.toString())
                    }
                    Log.d("khoa1", arrAudio.toString())
                    Log.d("khoa1", arrType.toString())
                    Log.d("khoa1", arrParts.toString())
                    binding.tvTitlePartsNumber.text = arrParts[0]

                }

                DataResult.Status.LOADING -> {showLoading(true)}
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

        binding.ivBackToHome.setOnClickListener {
            alertDialogReturnHome()
        }

        binding.tvComplete.setOnClickListener {
            alertDialogComplete()
        }

        val onClick = View.OnClickListener { view ->
            setUpViewPager(view)
        }
        binding.apply {
            ivNextParts.setOnClickListener(onClick)
            ivBackParts.setOnClickListener(onClick)
        }

        val part = arguments?.getString("part")
        binding.llFooterAudio.visibility = if(part == "read" || part == "5" || part == "6" || part == "7") View.GONE else View.VISIBLE

        binding.seekBarLuminosite.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar?) { mHandle.removeCallbacks(mUpdateTime) }
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

    @SuppressLint("SetTextI18n")
    private fun alertDialogReturnHome() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext(), R.style.Themecustom)
        val binding = CustomDialogSubmitPartsBinding.inflate(layoutInflater)
        alertDialogBuilder.setView(binding.root)
        alertDialogBuilder.setCancelable(false)

        binding.tvClose.setOnClickListener {
            dialog?.dismiss()
        }
        binding.tvTitle.text = "Bạn có muốn rời khỏi bài kiểm tra"
        binding.tvMessage.text = "Nếu rời khỏi bạn sẽ mất tiến trình hiện tại"

        binding.tvSubmit.setOnClickListener {
            dialog?.dismiss()
            findNavController().navigateUp()
        }

        dialog = alertDialogBuilder.create()
        dialog?.show()
    }

    private fun alertDialogComplete() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext(), R.style.Themecustom)
        val binding = CustomDialogSubmitPartsBinding.inflate(layoutInflater)
        alertDialogBuilder.setView(binding.root)
        alertDialogBuilder.setCancelable(false)

        binding.tvClose.setOnClickListener {
            dialog?.dismiss()
        }

        binding.tvSubmit.setOnClickListener {
            dialog?.dismiss()
            val part = arguments?.getString("part")
            val args = bundleOf("originResult" to partsDataAdapter.originRes(), "part" to part)
            Log.d("khoa2", "PartsFragment: ${partsDataAdapter.originRes().toString()}")
            findNavController().navigate(R.id.action_partsTestFragment_to_resultFragment,args,null)
        }

        dialog = alertDialogBuilder.create()
        dialog?.show()
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

            binding.viewPagerDataParts.currentItem = newPage
            handleVisible(newPage)
            handleMedia(newPage)
        }
    }

    private fun handleVisible(newPage: Int){
        binding.tvTitlePartsNumber.text = arrParts[newPage]
        binding.llFooterAudio.visibility = if(arrType[newPage] == "listen") View.VISIBLE else View.GONE
    }

    private fun handleMedia(newPage: Int) {
        Handler(Looper.getMainLooper()).postDelayed({
            try {
                kotlin.runCatching {
                    mediaPlayer!!.reset()
                    mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
                    mediaPlayer!!.setDataSource(
                        requireContext(), Uri.parse(arrAudio[newPage])
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

    private fun showLoading(isShow : Boolean){
        if(isShow){
            binding.progressBarShowLoading.visibility = View.VISIBLE
        } else {
            binding.progressBarShowLoading.visibility = View.GONE
        }
    }


}