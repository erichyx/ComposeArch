package com.eric.composearch

import android.app.Application
import com.eric.basicore.utils.LogUtil
import com.eric.composearch.utils.TEST_MODE
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by eric on 2022/5/6
 */
@HiltAndroidApp
class ComposeArchApp : Application() {
    override fun onCreate() {
        super.onCreate()
        LogUtil.level = if (TEST_MODE) LogUtil.VERBOSE else LogUtil.NOTHING
    }
}