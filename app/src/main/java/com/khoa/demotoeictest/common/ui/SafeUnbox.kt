package com.khoa.demotoeictest.common.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.khoa.demotoeictest.common.utils.Constant

// TODO: With xml resource
@BindingAdapter("srcCompat")
fun setImageResource(imageView: ImageView, resourceId: Int?) {
    resourceId?.let {
        imageView.setImageResource(it)
    }
}

object SafeUnbox {
    @JvmStatic
    fun getIconRes(icName: String?): Int? = Constant.iconParts[icName]
}