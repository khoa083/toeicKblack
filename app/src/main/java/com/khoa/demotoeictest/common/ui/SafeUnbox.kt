package com.khoa.demotoeictest.common.ui

import android.util.Log
import com.khoa.demotoeictest.common.utils.Constant

object SafeUnbox {
    @JvmStatic
    fun getIconRes(icName: String?): Int? {
        Log.d("iconParts", "${Constant.iconParts[icName]}")
        return Constant.iconParts[icName]
    }
}