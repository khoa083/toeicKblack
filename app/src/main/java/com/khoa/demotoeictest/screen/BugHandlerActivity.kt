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
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.khoa.demotoeictest.BuildConfig
import com.khoa.demotoeictest.R
import com.khoa.demotoeictest.utils.allowDiskAccessInStrictMode
import com.khoa.demotoeictest.utils.enableEdgeToEdgePaddingListener
import com.khoa.demotoeictest.utils.updateMargin
import com.khoa.demotoeictest.utils.hasOsClipboardDialog
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * BugHandlerActivity:
 *   An activity makes crash reporting easier.
 */
class BugHandlerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.BugHunter)
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        enableEdgeToEdge()
        setContentView(R.layout.activity_bug_handler)
        findViewById<View>(R.id.appbarlayout).enableEdgeToEdgePaddingListener()
        findViewById<MaterialToolbar>(R.id.topAppBar).setNavigationOnClickListener { finish() }
        onBackPressedDispatcher.addCallback { finish() }

        val bugText = findViewById<TextView>(R.id.error)
        val actionShare = findViewById<ExtendedFloatingActionButton>(R.id.actionShare)
        val exceptionMessage = intent.getStringExtra("exception_message")
        val threadName = intent.getStringExtra("thread")

        val deviceBrand = Build.BRAND
        val deviceModel = Build.MODEL
        val sdkLevel = Build.VERSION.SDK_INT
        val currentDateTime = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val formattedDateTime = formatter.format(currentDateTime)
        val versionName = BuildConfig.MY_VERSION_NAME

        val combinedTextBuilder = StringBuilder()
        combinedTextBuilder
            .append("Toeick Version")
            .append(':')
            .append(' ')
            .append(versionName)
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
        val baseLeft = actionShare.marginLeft
        val baseRight = actionShare.marginRight
        val baseBottom = actionShare.marginBottom
        bugText.enableEdgeToEdgePaddingListener {
            actionShare.updateMargin {
                left = baseLeft + it.left
                right = baseRight + it.right
                bottom = baseBottom + it.bottom
            }
        }

        // Make our life easier by copying the log to clipboard
        val clipboard: ClipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("error msg", combinedTextBuilder.toString())
        allowDiskAccessInStrictMode {
            clipboard.setPrimaryClip(clip)
        }
        if (!hasOsClipboardDialog()) {
            Toast.makeText(this, "Copied crash log to clipboard", Toast.LENGTH_LONG).show()
        }

        actionShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TITLE, "ToeicK Logs")
                putExtra(Intent.EXTRA_TEXT, bugText.text)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }
}

