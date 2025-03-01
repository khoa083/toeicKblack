/*
 *     Copyright (C) 2024 Akane Foundation
 *
 *     Gramophone is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Gramophone is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.khoa.demotoeictest.screen

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.os.StrictMode
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.khoa.demotoeictest.R
import com.squareup.leakcanary.core.BuildConfig
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * BugHandlerActivity:
 *   An activity makes crash reporting easier.
 */
class BugHandlerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        enableEdgeToEdge()
        setContentView(R.layout.activity_bug_handler)
//        findViewById<View>(R.id.appbarlayout).enableEdgeToEdgePaddingListener()
//        findViewById<MaterialToolbar>(R.id.topAppBar).setNavigationOnClickListener { finish() }
        onBackPressedDispatcher.addCallback { finish() }

        val bugText = findViewById<TextView>(R.id.error)
//        val actionShare = findViewById<ExtendedFloatingActionButton>(R.id.actionShare)
        val exceptionMessage = intent.getStringExtra("exception_message")
        val threadName = intent.getStringExtra("thread")

        val deviceBrand = Build.BRAND
        val deviceModel = Build.MODEL
        val sdkLevel = Build.VERSION.SDK_INT
        val currentDateTime = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val formattedDateTime = formatter.format(currentDateTime)
        val gramophoneVersion = "1.0.0"

        val combinedTextBuilder = StringBuilder()
        combinedTextBuilder
            .append("Toeic k ")
            .append(':')
            .append(' ')
            .append(gramophoneVersion)
            .append('\n')
            .append('\n')
            .append("Brand")
            .append(':')
            .append("     ")
            .append(deviceBrand)
            .append('\n')
            .append("Model")
            .append(':')
            .append("     ")
            .append(deviceModel)
            .append('\n')
            .append("SDK level")
            .append(':')
            .append(' ')
            .append(sdkLevel)
            .append('\n')
            .append("Theard")
            .append(':')
            .append("    ")
            .append(threadName)
            .append('\n')
            .append('\n')
            .append('\n')
            .append("Crash Time")
            .append(':')
            .append(' ')
            .append(formattedDateTime)
            .append('\n')
            .append("--------- beginning of crash")
            .append('\n')
            .append(exceptionMessage)

        bugText.typeface = Typeface.MONOSPACE
        bugText.text = combinedTextBuilder.toString()
//        val baseLeft = actionShare.marginLeft
//        val baseRight = actionShare.marginRight
//        val baseBottom = actionShare.marginBottom
//        bugText.enableEdgeToEdgePaddingListener {
//            actionShare.updateMargin {
//                left = baseLeft + it.left
//                right = baseRight + it.right
//                bottom = baseBottom + it.bottom
//            }
//        }

        // Make our life easier by copying the log to clipboard
        val clipboard: ClipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("error msg", combinedTextBuilder.toString())
        allowDiskAccessInStrictMode {
            clipboard.setPrimaryClip(clip)
        }
        if (!hasOsClipboardDialog()) {
            Toast.makeText(this, "Copied crash log to clipboard", Toast.LENGTH_LONG).show()
        }

//        actionShare.setOnClickListener {
//            val sendIntent: Intent = Intent().apply {
//                action = Intent.ACTION_SEND
//                putExtra(Intent.EXTRA_TITLE, "Gramophone Logs")
//                putExtra(Intent.EXTRA_TEXT, bugText.text)
//                type = "text/plain"
//            }
//            val shareIntent = Intent.createChooser(sendIntent, null)
//            startActivity(shareIntent)
//        }
    }
    
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
    
    @Suppress("NOTHING_TO_INLINE")
    inline fun hasOsClipboardDialog(): Boolean =
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
    
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
}

