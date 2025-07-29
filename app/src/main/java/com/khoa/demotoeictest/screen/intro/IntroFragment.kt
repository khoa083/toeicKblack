package com.khoa.demotoeictest.screen.intro

import android.text.SpannableString
import android.text.style.UnderlineSpan
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.kblack.base.BaseFragment
import com.khoa.demotoeictest.MainActivity
import com.khoa.demotoeictest.R
import com.khoa.demotoeictest.databinding.FragmentIntroBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.abs

class IntroFragment: BaseFragment<FragmentIntroBinding, Nothing?>() {

    override val viewModel = null
    override val layoutId = R.layout.fragment_intro

    private val introItems by lazy { createIntroItems() }
    private var autoScrollJob: Job? = null

    override fun setupView() {
        hideBottomNavigation()
        setupSkipButton()
        setupViewPager()
        startAutoScroll()
    }

    private fun hideBottomNavigation() {
        (activity as? MainActivity)?.handleShowBottomNav(false)
    }

    private fun setupSkipButton() {
        binding.tvSkip.apply {
            text = text.toString().toUnderlineSpan()
            setOnClickListener {
                findNavController().navigate(R.id.action_introFragment_to_homeFragment)
            }
        }
    }

    private fun setupViewPager() {
        binding.viewPagerIntro.apply {
            adapter = TabViewPagerAdapter(introItems, this)
            offscreenPageLimit = OFFSCREEN_PAGE_LIMIT as Int

            configureScrollBehavior()
            setPageTransformer(createPageTransformer())

            binding.dotIndicator.attachTo(this)
        }
    }

    private fun ViewPager2.configureScrollBehavior() {
        clipToPadding = false
        clipChildren = false
        (getChildAt(0) as? RecyclerView)?.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }

    private fun createPageTransformer(): CompositePageTransformer {
        return CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(MARGIN_DP))
            addTransformer { page, position ->
                val scaleFactor = MIN_SCALE + (1 - abs(position)) * SCALE_FACTOR
                page.scaleX = scaleFactor
                page.scaleY = scaleFactor
            }
        }
    }

    private fun startAutoScroll() {
        autoScrollJob = viewLifecycleOwner.lifecycleScope.launch {
            while (isActive && introItems.isNotEmpty()) {
                delay(AUTO_SCROLL_DELAY_MS)

                val nextItem = binding.viewPagerIntro.run {
                    if (currentItem >= introItems.size - 1) 0 else currentItem + 1
                }

                binding.viewPagerIntro.setCurrentItem(nextItem, true)
            }
        }
    }

    private fun createIntroItems(): ArrayList<ItemIntro> = arrayListOf(
        ItemIntro(
            imageRes = R.drawable.img_intro_1,
            title = "Numerous free trial courses",
            description = "Free courses for you to find your way to learning"
        ),
        ItemIntro(
            imageRes = R.drawable.img_intro_2,
            title = "Quick and easy learning",
            description = "Easy and fast learning at any time to help you improve various skills"
        ),
        ItemIntro(
            imageRes = R.drawable.img_intro_3,
            title = "Create your own study plan",
            description = "Study according to the study plan, make study more motivated"
        )
    )

    override fun onDestroy() {
        autoScrollJob?.cancel()
        super.onDestroy()
    }

    companion object {
        private const val AUTO_SCROLL_DELAY_MS = 3000L
        private const val OFFSCREEN_PAGE_LIMIT = 3
        private const val MARGIN_DP = 40
        private const val MIN_SCALE = 0.85f
        private const val SCALE_FACTOR = 0.14f
    }
}

private fun String.toUnderlineSpan(): SpannableString {
    return SpannableString(this).apply {
        setSpan(UnderlineSpan(), 0, length, 0)
    }
}