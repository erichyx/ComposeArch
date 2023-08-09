package com.eric.composearch.utils

import android.content.Context
import com.eric.basicore.utils.showToast
import com.eric.composearch.R

/**
 * Created by eric on 2022/7/8
 */
class TapBackTwiceWatcher {
    fun dealBackPressed(context: Context, onFinish: () -> Unit) {
        if (System.currentTimeMillis() - lastTapTime > 1500) {
            showToast(context, R.string.toast_exit_app)
            lastTapTime = System.currentTimeMillis()
        } else {
            onFinish()
        }
    }

    companion object {
        private var lastTapTime = 0L
    }
}