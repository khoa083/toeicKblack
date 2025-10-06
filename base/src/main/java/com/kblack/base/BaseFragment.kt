package com.kblack.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

abstract class BaseFragment<VB: ViewDataBinding, VM: ViewModel?> : Fragment() {
    private var _binding: VB? = null
    protected val binding get() = _binding!!
    protected abstract val viewModel: VM?

//    ViewBinding abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    /**
     * override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentGeneratePassphraseBinding =
     *     FragmentGeneratePassphraseBinding::inflate
     */

    abstract val layoutId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        ViewBinding _binding = bindingInflater(inflater, container, false)
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    abstract fun setupView()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun findFragment(tag: String): Fragment? {
        return activity?.supportFragmentManager?.findFragmentByTag(tag)
    }

    protected fun findChildFragment(parentFragment: Fragment = this, tag: String): Fragment? {
        return parentFragment.childFragmentManager.findFragmentByTag(tag)
    }

}