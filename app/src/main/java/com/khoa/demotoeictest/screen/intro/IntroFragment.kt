package com.khoa.demotoeictest.screen.intro

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.khoa.demotoeictest.MainActivity
import com.khoa.demotoeictest.R
import com.khoa.demotoeictest.databinding.FragmentIntroBinding
import java.util.Timer
import java.util.TimerTask
import kotlin.math.abs

@Suppress("DEPRECATION")
class IntroFragment : Fragment() {

    private lateinit var binding: FragmentIntroBinding
    private var itemIntro: ArrayList<ItemIntro> = ArrayList()
    private var timer: Timer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_intro,container,false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListener()
        handleTextUnderline()
        setUpNavigationFragment()
        initView()
    }

    private fun initView(){
        (activity as MainActivity).handleShowBottomNav(false)
    }

    private fun setUpNavigationFragment() {
        binding.tvSkip.setOnClickListener {
            findNavController().navigate(R.id.action_introFragment_to_homeFragment)
        }
    }

    private fun handleTextUnderline() {
        val str = binding.tvSkip.text
        val spannableString = SpannableString(str.toString())
        spannableString.setSpan(UnderlineSpan(),0,spannableString.length,0)
        binding.tvSkip.text = spannableString
    }

    private fun setUpListener() {
        itemIntro.add(
            ItemIntro(
                R.drawable.img_intro_1,
                "Numerous free trial courses",
                "Free courses for you to find your way to learning"
            )
        )
        itemIntro.add(
            ItemIntro(
                R.drawable.img_intro_2,
                "Quick and easy learning",
                "Easy and fast learning at any time to help you improve various skills"
            )
        )
        itemIntro.add(
            ItemIntro(
                R.drawable.img_intro_3,
                "Create your own study plan",
                "Study according to the study plan, make study more motivated"
            )
        )


        val vpIntro = binding.viewPagerIntro
        vpIntro.adapter = TabViewPagerAdapter(itemIntro, vpIntro)


        vpIntro.clipToPadding = false
        vpIntro.clipChildren = false
        vpIntro.offscreenPageLimit = 3
        vpIntro.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))

        compositePageTransformer.addTransformer { page, position ->
            val r: Float = 1 - abs(position)
//            page.scaleX = (0.85f + r * 0.15f)
            page.scaleY = 0.85f + r * 0.14f
            page.scaleX = 0.85f + r * 0.14f
        }

        vpIntro.setPageTransformer(compositePageTransformer)
        val sizeList = itemIntro.size
        var currentPage = 0

        val handler = Handler(Looper.getMainLooper())
        val update = Runnable {
            if (currentPage == sizeList) {
                currentPage = 0
            }
            vpIntro.setCurrentItem(currentPage++, true)
        }

        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, 3000, 3000)
        binding.dotIndicator.setViewPager2(vpIntro)
//        setUpTransformer()

    }

//    private fun setUpTransformer() {
//        val transformer = CompositePageTransformer()
//        transformer.addTransformer(MarginPageTransformer(30))
//        transformer.addTransformer { page, position ->
//            val r = 1 - abs(position)
//            page.scaleY = 0.85f + r * 0.14f
//            page.scaleX = 0.85f + r * 0.14f
//            val marginLeft = 120
//            val marginRight = 140
//            val marginCenter = 140
//            val offset: Float = when {
//                position <= -1 -> position * marginLeft
//                position >= 1 -> position * -marginRight
//                else -> (-marginCenter).toFloat()
//            }
//            page.translationX = offset
//
//        }
//        binding.viewPagerIntro.setPageTransformer(transformer)
//    }

}