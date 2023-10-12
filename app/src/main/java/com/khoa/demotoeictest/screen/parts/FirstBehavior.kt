package com.khoa.demotoeictest.screen.parts

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout


class FirstBehavior(context: Context?, attrs: AttributeSet?) : CoordinatorLayout.Behavior<View>(context, attrs) {

    @SuppressLint("SetTextI18n")
    override fun onInterceptTouchEvent(parent: CoordinatorLayout, child: View, ev: MotionEvent): Boolean {
        child as TextView
        child.text = "${ev.action} | ${ev.x.toInt()} | ${ev.y.toInt()}"
        return true
    }

    @SuppressLint("SetTextI18n")
    override fun onTouchEvent(parent: CoordinatorLayout, child: View, ev: MotionEvent): Boolean {
        child as TextView
        child.text = "${ev.action} | ${ev.x.toInt()} | ${ev.y.toInt()}"
        return true
    }



}