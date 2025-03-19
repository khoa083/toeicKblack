package com.khoa.demotoeictest.common.utils

import android.os.Build
import android.os.Looper
import android.os.StrictMode
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.khoa.demotoeictest.BuildConfig

@Suppress("NOTHING_TO_INLINE")
inline fun hasOsClipboardDialog(): Boolean =
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU

inline fun <reified T> allowDiskAccessInStrictMode(relax: Boolean = false, doIt: () -> T): T {
    return if (BuildConfig.DEBUG) {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            if (relax) doIt() else
                throw IllegalStateException("allowDiskAccessInStrictMode(false) on wrong thread")
        } else {
            val policy = StrictMode.allowThreadDiskReads()
            try {
                StrictMode.allowThreadDiskWrites()
                doIt()
            } finally {
                StrictMode.setThreadPolicy(policy)
            }
        }
    } else doIt()
}

fun View.enableEdgeToEdgePaddingListener(
        ime: Boolean = false, top: Boolean = false,
        extra: ((Insets) -> Unit)? = null
) {
    if (fitsSystemWindows) throw IllegalArgumentException("must have fitsSystemWindows disabled")
    if (this is AppBarLayout) {
        if (ime) throw IllegalArgumentException("AppBarLayout must have ime flag disabled")
        // AppBarLayout fitsSystemWindows does not handle left/right for a good reason, it has
        // to be applied to children to look good; we rewrite fitsSystemWindows in a way mostly specific
        // to Gramophone to support shortEdges displayCutout
        val collapsingToolbarLayout =
            children.find { it is CollapsingToolbarLayout } as CollapsingToolbarLayout?
        collapsingToolbarLayout?.let {
            // The CollapsingToolbarLayout mustn't consume insets, we handle padding here anyway
            ViewCompat.setOnApplyWindowInsetsListener(it) { _, insets -> insets }
        }
        val expandedTitleMarginStart = collapsingToolbarLayout?.expandedTitleMarginStart
        val expandedTitleMarginEnd = collapsingToolbarLayout?.expandedTitleMarginEnd
        ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
            val cutoutAndBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
                        or WindowInsetsCompat.Type.displayCutout()
            )
            (v as AppBarLayout).children.forEach {
                if (it is CollapsingToolbarLayout) {
                    val es = expandedTitleMarginStart!! + if (it.layoutDirection
                        == View.LAYOUT_DIRECTION_LTR
                    ) cutoutAndBars.left else cutoutAndBars.right
                    if (es != it.expandedTitleMarginStart) it.expandedTitleMarginStart = es
                    val ee = expandedTitleMarginEnd!! + if (it.layoutDirection
                        == View.LAYOUT_DIRECTION_RTL
                    ) cutoutAndBars.left else cutoutAndBars.right
                    if (ee != it.expandedTitleMarginEnd) it.expandedTitleMarginEnd = ee
                }
                it.setPadding(cutoutAndBars.left, 0, cutoutAndBars.right, 0)
            }
            v.setPadding(0, cutoutAndBars.top, 0, 0)
            val i = insets.getInsetsIgnoringVisibility(
                WindowInsetsCompat.Type.systemBars()
                        or WindowInsetsCompat.Type.displayCutout()
            )
            extra?.invoke(cutoutAndBars)
            return@setOnApplyWindowInsetsListener WindowInsetsCompat.Builder(insets)
                    .setInsets(
                        WindowInsetsCompat.Type.systemBars()
                                or WindowInsetsCompat.Type.displayCutout(),
                        Insets.of(cutoutAndBars.left, 0, cutoutAndBars.right, cutoutAndBars.bottom)
                    )
                    .setInsetsIgnoringVisibility(
                        WindowInsetsCompat.Type.systemBars()
                                or WindowInsetsCompat.Type.displayCutout(),
                        Insets.of(i.left, 0, i.right, i.bottom)
                    )
                    .build()
        }
    } else {
        val pl = paddingLeft
        val pt = paddingTop
        val pr = paddingRight
        val pb = paddingBottom
        ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
            val mask = WindowInsetsCompat.Type.systemBars() or
                    WindowInsetsCompat.Type.displayCutout() or
                    if (ime) WindowInsetsCompat.Type.ime() else 0
            val i = insets.getInsets(mask)
            v.setPadding(
                pl + i.left, pt + (if (top) i.top else 0), pr + i.right,
                pb + i.bottom
            )
            extra?.invoke(i)
            return@setOnApplyWindowInsetsListener WindowInsetsCompat.Builder(insets)
                    .setInsets(mask, Insets.NONE)
                    .setInsetsIgnoringVisibility(mask, Insets.NONE)
                    .build()
        }
    }
}

data class Margin(var left: Int, var top: Int, var right: Int, var bottom: Int) {
    companion object {
        @Suppress("NOTHING_TO_INLINE")
        internal inline fun fromLayoutParams(marginLayoutParams: MarginLayoutParams): Margin {
            return Margin(
                marginLayoutParams.leftMargin, marginLayoutParams.topMargin,
                marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin
            )
        }
    }
    
    @Suppress("NOTHING_TO_INLINE")
    internal inline fun apply(marginLayoutParams: MarginLayoutParams) {
        marginLayoutParams.updateMargins(left, top, right, bottom)
    }
}


fun View.updateMargin(
        block: Margin.() -> Unit
) {
    val oldMargin = Margin.fromLayoutParams(layoutParams as MarginLayoutParams)
    val newMargin = oldMargin.copy().also { it.block() }
    if (oldMargin != newMargin) {
        updateLayoutParams<MarginLayoutParams> {
            newMargin.apply(this)
        }
    }
}