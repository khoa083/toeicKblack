package com.khoa.demotoeictest.screen.splash

import android.os.Handler
import android.os.Looper
import androidx.navigation.fragment.findNavController
import com.kblack.base.BaseFragment
import com.khoa.demotoeictest.MainActivity
import com.khoa.demotoeictest.R
import com.khoa.demotoeictest.databinding.FragmentSplashBinding

class SplashFragment : BaseFragment<FragmentSplashBinding, Nothing?>() {
    override val viewModel = null
    override val layoutId = R.layout.fragment_splash

    override fun setupView() {
        (activity as MainActivity).handleShowBottomNav(false)
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_splashFragment_to_introFragment)
        },1000)
    }
}