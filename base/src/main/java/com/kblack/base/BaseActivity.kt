package com.kblack.base

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

abstract class BaseActivity<VB: ViewDataBinding, VM: ViewModel?> : AppCompatActivity() {
    private var _binding: VB? = null
    protected val binding get() = _binding!!

    abstract val viewModel: VM?
    abstract val layoutId: Int
    abstract val idContainerView: Int

    private fun setSystemBars() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(idContainerView)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
        setSystemBars()
        setupView()
    }

    abstract fun setupView()

    abstract fun handleShowBottomNav(isShow: Boolean = false)

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}