package com.khoa.demotoeictest.common.ui

import com.khoa.demotoeictest.common.utils.Constant

object SafeUnbox {
    @JvmStatic
    fun getIconRes(icName: String): Int? = Constant.iconParts[icName]
}